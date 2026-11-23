package com.mtgcollectionorganizer.api.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new SecurityConfiguration(null).passwordEncoder();

    @Test
    @DisplayName("deve gerar hashes argon2id com salt aleatório")
    void shouldGenerateArgon2idHashWithRandomSalt() {
        final String firstHash = passwordEncoder.encode("senha123");
        final String secondHash = passwordEncoder.encode("senha123");

        assertTrue(firstHash.startsWith("{argon2id}$argon2id$"));
        assertNotEquals(firstHash, secondHash);
    }

    @Test
    @DisplayName("deve validar a senha correta e rejeitar a incorreta")
    void shouldMatchOnlyCorrectPassword() {
        final String hash = passwordEncoder.encode("senha123");

        assertTrue(passwordEncoder.matches("senha123", hash));
        assertFalse(passwordEncoder.matches("errada", hash));
    }

    @Test
    @DisplayName("deve validar hashes bcrypt legados com o prefixo do delegating encoder")
    void shouldMatchLegacyBcryptHash() {
        final String legacyHash = "{bcrypt}" + new BCryptPasswordEncoder().encode("senha123");

        assertTrue(passwordEncoder.matches("senha123", legacyHash));
        assertFalse(passwordEncoder.matches("errada", legacyHash));
    }
}
