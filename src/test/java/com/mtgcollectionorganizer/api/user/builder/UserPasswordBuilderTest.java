package com.mtgcollectionorganizer.api.user.builder;

import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPasswordBuilderTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserPasswordBuilder userPasswordBuilder;

    @Test
    @DisplayName("deve fazer hash apenas da senha, sem concatenar o username")
    void shouldHashOnlyThePassword() {
        final UserEntity user = UserEntity.builder().id("user-id").userName("guilherme").build();
        when(passwordEncoder.encode("senha123")).thenReturn("hash");

        final UserPasswordEntity userPassword = userPasswordBuilder
                .build("senha123", user);

        verify(passwordEncoder).encode("senha123");
        verify(passwordEncoder, never()).encode("guilhermesenha123");
        assertEquals("hash", userPassword.getPassword());
        assertEquals("guilherme", userPassword.getUsername());
        assertTrue(userPassword.isEnabled());
    }
}
