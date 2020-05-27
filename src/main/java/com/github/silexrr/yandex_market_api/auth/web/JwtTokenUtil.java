package com.github.silexrr.yandex_market_api.auth.web;


import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import com.github.silexrr.yandex_market_api.api.model.APIToken;
import com.github.silexrr.yandex_market_api.api.service.APITokenService;
import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private ShopService shopService;

    @Autowired
    private APITokenService apiTokenService;

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
    public String getUserIdFromToken(String token) {
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
     * @param User
     * @return
     */
    public String generateToken (User User) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, User.getId());
    }

    /**
     * Creating token for user with claims
     * @param user
     * @return
     */
    public String generateToken (User user, Map claims) {
        return doGenerateToken(claims, user.getId());
    }

    /**
     * The process of building, adding lifetime and signature using the HS256 algorithm
     * @param claims
     * @param subject
     * @return
     */
    private String doGenerateToken (Map<String, Object> claims, String subject) {
        System.out.println(claims);
        System.out.println(subject);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        Date expirationDate = calendar.getTime();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                    .setIssuedAt(new Date())
                    .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Shop getShopFromToken (String jwtStr) {
        Shop shop = null;

        Claims allClaimsFromToken = this.getAllClaimsFromToken(jwtStr);
        String shopId = (String)allClaimsFromToken.get("shopId");
        Optional<Shop> optionalShop = shopService.findById(shopId);
        if(optionalShop.isEmpty() == false) {
            shop = optionalShop.get();
        }
        return shop;
    }

    /**
     * validate token for some user
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken (
            String token
            , UserDetails userDetails
//            , StringBuilder error
    ) {
        User user = (User)userDetails;
        final String username = getUserIdFromToken(token);
        if(username.equals(user.getId()) == false
                || isTokenExpired(token)
        ) {
            return false;
        }
        APIToken apiToken = apiTokenService.findByToken(token);
        if (apiToken == null) {
//            error.append("This token was deleted");
            return false;
        }
        if (apiToken.isEnable() == false) {
//            error.append("This token is disabled");
            return false;
        }
        return true;
    }
}
