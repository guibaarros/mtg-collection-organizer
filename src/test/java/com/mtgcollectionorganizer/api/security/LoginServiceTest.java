package com.mtgcollectionorganizer.api.security;

import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import com.mtgcollectionorganizer.api.user.exception.UserNotAuthorizedException;
import com.mtgcollectionorganizer.api.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @Test
    @DisplayName("senha correta deve autenticar e gerar o token")
    void shouldLoginWithCorrectPassword() {
        final UserPasswordEntity userPassword = userPassword();
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPassword, "hash", userPassword.getAuthorities());

        when(userService.getUserPasswordByUserName("guilherme")).thenReturn(userPassword);
        when(passwordEncoder.matches("senha123", "hash")).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateToken(userPassword)).thenReturn("jwt");

        assertEquals("jwt", loginService.login("guilherme", "senha123"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(userPassword);
    }

    @Test
    @DisplayName("senha incorreta deve lançar UserNotAuthorizedException")
    void shouldRejectWrongPassword() {
        when(userService.getUserPasswordByUserName("guilherme")).thenReturn(userPassword());
        when(passwordEncoder.matches("errada", "hash")).thenReturn(false);

        assertThrows(UserNotAuthorizedException.class,
                () -> loginService.login("guilherme", "errada"));

        verifyNoInteractions(authenticationManager, tokenService);
    }

    @Test
    @DisplayName("getUsernameByToken deve remover o prefixo Bearer")
    void shouldReadUsernameRemovingBearerPrefix() {
        when(tokenService.getSubject("jwt")).thenReturn("guilherme");

        assertEquals("guilherme", loginService.getUsernameByToken("Bearer jwt"));
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
