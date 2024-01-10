package com.api.kiranastore.security;

import com.api.kiranastore.enums.Roles;
import com.api.kiranastore.exception.TokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenUtils {

    @Value("${jwt.secret}")
    private String SECRET;

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<Roles> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<Roles>) claims.get("Roles");
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userId, List<Roles> roles) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, roles);
    }

    private String createToken(Map<String, Object> claims, String userId, List<Roles> roles) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .claim("Roles",roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) //10 mins
                .signWith(getSignkey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
