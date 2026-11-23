package com.mtgcollectionorganizer.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenServiceTest {

    private static final String SECRET = "test-secret";
    private static final String ISSUER = "test-issuer";

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", SECRET);
        ReflectionTestUtils.setField(tokenService, "issuer", ISSUER);
    }

    @Test
    @DisplayName("generateToken() e getSubject() devem fazer um round-trip")
    void shouldGenerateAndReadToken() {
        final String token = tokenService.generateToken(userPassword());

        assertEquals("guilherme", tokenService.getSubject(token));
    }

    @Test
    @DisplayName("deve rejeitar token assinado com outro secret")
    void shouldRejectTokenWithDifferentSecret() {
        final String token = JWT.create()
                .withIssuer(ISSUER)
                .withSubject("guilherme")
                .sign(Algorithm.HMAC256("outro-secret"));

        assertThrows(JWTVerificationException.class, () -> tokenService.getSubject(token));
    }

    @Test
    @DisplayName("deve rejeitar token com issuer diferente")
    void shouldRejectTokenWithDifferentIssuer() {
        final String token = JWT.create()
                .withIssuer("outro-issuer")
                .withSubject("guilherme")
                .sign(Algorithm.HMAC256(SECRET));

        assertThrows(JWTVerificationException.class, () -> tokenService.getSubject(token));
    }

    @Test
    @DisplayName("deve rejeitar token expirado")
    void shouldRejectExpiredToken() {
        final String token = JWT.create()
                .withIssuer(ISSUER)
                .withSubject("guilherme")
                .withExpiresAt(Instant.now().minus(1, ChronoUnit.MINUTES))
                .sign(Algorithm.HMAC256(SECRET));

        assertThrows(JWTVerificationException.class, () -> tokenService.getSubject(token));
    }

    private static UserPasswordEntity userPassword() {
        final UserEntity user = UserEntity.builder()
                .id("user-id")
                .userName("guilherme")
                .build();

        return UserPasswordEntity.builder()
                .id("password-id")
                .encryptedPassword("hash")
                .active(true)
                .user(user)
                .build();
    }
}
