package com.mtgcollectionorganizer.api.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import com.mtgcollectionorganizer.api.user.exception.UserNotFoundException;
import com.mtgcollectionorganizer.api.user.repository.UserPasswordRepository;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilterTokenTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserPasswordRepository userRepository;

    @InjectMocks
    private FilterToken filter;

    @BeforeEach
    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("sem header Authorization deve seguir a cadeia sem autenticar")
    void shouldContinueChainWithoutAuthorizationHeader() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
        verifyNoInteractions(tokenService, userRepository);
    }

    @Test
    @DisplayName("com Bearer válido deve autenticar e seguir a cadeia")
    void shouldAuthenticateWithValidBearerToken() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-valido");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);

        when(tokenService.getSubject("token-valido")).thenReturn("guilherme");
        when(userRepository.findByUserNameAndIsActive("guilherme"))
                .thenReturn(Optional.of(userPassword()));

        filter.doFilter(request, response, chain);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("guilherme", authentication.getPrincipal());
        assertEquals("hash", authentication.getCredentials());
        assertTrue(authentication.getAuthorities().stream()
                .anyMatch(authority -> "role".equals(authority.getAuthority())));
        verify(chain).doFilter(request, response);
    }

    @Test
    @DisplayName("com usuário inexistente deve lançar UserNotFoundException")
    void shouldThrowWhenUserNotFound() {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-valido");

        when(tokenService.getSubject("token-valido")).thenReturn("inexistente");
        when(userRepository.findByUserNameAndIsActive("inexistente")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> filter.doFilter(request, new MockHttpServletResponse(), mock(FilterChain.class)));
    }

    @Test
    @DisplayName("com token inválido deve propagar a exceção de verificação")
    void shouldPropagateInvalidTokenException() {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-invalido");

        when(tokenService.getSubject("token-invalido"))
                .thenThrow(new JWTVerificationException("token inválido"));

        assertThrows(JWTVerificationException.class,
                () -> filter.doFilter(request, new MockHttpServletResponse(), mock(FilterChain.class)));
    }

    @Test
    @DisplayName("sem o prefixo Bearer o token é usado integralmente")
    void shouldUseRawTokenWhenBearerPrefixIsMissing() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "token-cru");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);

        when(tokenService.getSubject("token-cru")).thenReturn("guilherme");
        when(userRepository.findByUserNameAndIsActive("guilherme"))
                .thenReturn(Optional.of(userPassword()));

        filter.doFilter(request, response, chain);

        verify(tokenService).getSubject("token-cru");
        verify(chain).doFilter(request, response);
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
