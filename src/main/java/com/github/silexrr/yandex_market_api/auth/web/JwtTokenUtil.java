package com.github.silexrr.yandex_market_api.auth.web;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    /**
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T getClaimFromToken (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     *
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken (String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Return username from token
     * @param token
     * @return
     */
    public String getUserNameFromToken (String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Return expiration date from token
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken (String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Checking token for expiration
     * @param token
     * @return
     */
    private Boolean isTokenExpired (String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Creating token for user
     * @param userDetails
     * @return
     */
    public String generateToken (UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * The process of building, adding lifetime and signature using the HS512 algorithm
     * @param claims
     * @param subject
     * @return
     */
    private String doGenerateToken (Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() * JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * validate token for some user
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken (String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
