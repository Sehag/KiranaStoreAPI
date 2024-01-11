package com.api.kiranastore.core_auth.security;

import com.api.kiranastore.core_auth.services.apiRateLimit.RateLimitServiceImpl;
import com.api.kiranastore.core_auth.services.auth.RefreshTokenServiceImpl;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.exception.TokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final UserInfoService userInfoService;
    private final RateLimitServiceImpl rateLimitService;
    private final RefreshTokenServiceImpl refreshTokenService;

    public AuthFilter(
            TokenUtils tokenUtils,
            UserInfoService userInfoService,
            RateLimitServiceImpl rateLimitService,
            RefreshTokenServiceImpl refreshTokenService) {
        this.tokenUtils = tokenUtils;
        this.userInfoService = userInfoService;
        this.rateLimitService = rateLimitService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String id = null;
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                id = tokenUtils.extractUserId(token);
            } else {
                throw new TokenException(
                        false, HttpStatus.UNAUTHORIZED, null, "Token Not found in Header", 401);
            }

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (!tokenUtils.isTokenExpired(token)) {
                    String role = String.valueOf(tokenUtils.extractRoles(token).get(0));
                    if (!rateLimitService.isRateLimitBreached(role)) {
                        UserInfoDetails userInfoDetails = userInfoService.loadUserByUsername(id);
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userInfoDetails, null, userInfoDetails.getAuthorities());
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        throw new TokenException(
                                false,
                                HttpStatus.TOO_MANY_REQUESTS,
                                null,
                                "Wait for a while before you proceed",
                                429);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (TokenException e) {
            response = createResponse(response, e);
        } catch (JwtException e) {
            TokenException err;
            if (refreshTokenService.isAvailable(id)) {
                String accessToken = tokenUtils.generateToken(id, tokenUtils.extractRoles(token));
                err =
                        new TokenException(
                                true,
                                HttpStatus.OK,
                                accessToken,
                                "Access token was expired, here is the new token",
                                200);
            } else {
                err =
                        new TokenException(
                                false,
                                HttpStatus.BAD_REQUEST,
                                null,
                                "Access token and refresh token expired, proceed to login page",
                                400);
            }
            response = createResponse(response, err);
        } catch (ServletException s) {
            TokenException err =
                    new TokenException(
                            false,
                            HttpStatus.BAD_REQUEST,
                            null,
                            "Request cannot be understood",
                            400);
            response = createResponse(response, err);
        } catch (IOException io) {
            TokenException err =
                    new TokenException(false, HttpStatus.BAD_REQUEST, null, "IO exception", 400);
            response = createResponse(response, err);
        }
    }

    /**
     * Exclude login and signup APIs from authenticating tokens
     *
     * @param request Http request
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/api/home/login") || path.equals("/api/home/signup");
    }

    private HttpServletResponse createResponse(HttpServletResponse response, TokenException e)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(e.getApiResponse().getHttpStatusCode());
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(e.getApiResponse()));
        return response;
    }
}
