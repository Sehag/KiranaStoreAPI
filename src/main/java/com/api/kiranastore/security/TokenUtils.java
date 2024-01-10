package com.api.kiranastore.security;

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
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenUtils {

    @Value("${jwt.secret}")
    private String SECRET;

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
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
        System.out.println("Time Expiry check");
        System.out.println("exp time: " + extractExpiration(token));
        System.out.println("Time now: " + new Date());
        System.out.println("bool value : " + extractExpiration(token).before(new Date()));

        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String id) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, id);
    }

    private String createToken(Map<String, Object> claims, String id) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) //10 mins
                .signWith(getSignkey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
