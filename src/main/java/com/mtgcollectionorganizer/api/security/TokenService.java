package com.mtgcollectionorganizer.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {
    
    @Value("${mtgorg.jwt.sign.secret:mtgorg-local-default}")
    private String secret;

    @Value("${mtgorg.jwt.issuer:mtgorg}")
    private String issuer;
    
    
    public String generateToken(final UserPasswordEntity userPassword) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userPassword.getUsername())
                .withClaim("id", userPassword.getUser().getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(30)
                        .atZone(ZoneId.systemDefault()).toInstant()
                ).sign(Algorithm.HMAC256(secret));
    }

    public String getSubject(final String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }
}
