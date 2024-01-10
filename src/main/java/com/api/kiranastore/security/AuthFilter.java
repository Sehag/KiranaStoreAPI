package com.api.kiranastore.security;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.enums.Roles;
import com.api.kiranastore.exception.TokenException;
import com.api.kiranastore.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jodd.util.StringUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final UserInfoService userInfoService;

    public AuthFilter(TokenUtils tokenUtils, UserInfoService userInfoService) {
        this.tokenUtils = tokenUtils;
        this.userInfoService = userInfoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String id = null;
        try{
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                id = tokenUtils.extractUserId(token);
            } else {
                throw new TokenException(HttpStatus.UNAUTHORIZED, "Token Not found in Header", "401");
            }

            /**
             * TODO: Have roles in jwt.
             * TODO: pass jwt to userDetails -> In Userdetails use jwt to overirde granted authority.
             */

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserInfoDetails userInfoDetails = userInfoService.loadUserByUsername(id);
                System.out.println("Authority running next!");
                Collection<? extends GrantedAuthority> authorities = userInfoDetails.getAuthorities();
                System.out.println("Authority got: " + authorities);

                if (!tokenUtils.isTokenExpired(token)) {
                    System.out.println("Token entered expiry zone1");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userInfoDetails, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch(TokenException e){
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(e.getApiResponse()));
        } catch (JwtException e){
            TokenException err = new TokenException(HttpStatus.UNAUTHORIZED,"Your access token has expired","401");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(err.getApiResponse()));
        } catch (ServletException s){
            System.out.println("server");
        } catch (IOException io){
            System.out.println("io");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        String path = request.getRequestURI();
        return path.equals("/api/home/login") || path.equals("/api/home/signup");
    }

    /*
    private HttpServletResponse createResponse(HttpServletResponse response,Exception e) throws IOException{
        //TokenException e = new TokenException(HttpStatus.UNAUTHORIZED,"Your access token has expired","401");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(e.getApiResponse()));
        return response;
    }

     */
}
