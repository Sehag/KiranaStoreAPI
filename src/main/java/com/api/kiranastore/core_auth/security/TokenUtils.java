package com.api.kiranastore.core_auth.security;

import com.api.kiranastore.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenUtils {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.accessToken.expiration.time}")
    private long expirationTime;

    /**
     * Extract userId from the access token
     *
     * @param token access token
     * @return userId
     */
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract all the roles assigned the user from access token
     *
     * @param token access token
     * @return List of roles
     */
    public List<Roles> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<Roles>) claims.get("Roles");
    }

    /**
     * Extract expiration time of the access token
     *
     * @param token access token
     * @return expiration time
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract the claims from the access token
     *
     * @param token access token
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all the claims from the access token
     *
     * @param token access token
     * @return all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if the access token is expired
     *
     * @param token access token
     * @return true/false
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a new access token for the authenticated user
     *
     * @param userId user Id
     * @param roles roles assigned for the user
     * @return valid access token
     */
    public String generateToken(String userId, List<Roles> roles) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, roles);
    }

    /**
     * Creates an access token
     *
     * @param claims claims
     * @param userId user Id
     * @param roles roles for the user
     * @return access token
     */
    private String createToken(Map<String, Object> claims, String userId, List<Roles> roles) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .claim("Roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expirationTime))
                .signWith(getSignkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retrieves the signing key used for access token creation and validation.
     *
     * @return signing key
     */
    private Key getSignkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
