package com.mtgcollectionorganizer.api.security;

import com.mtgcollectionorganizer.api.security.LoginService;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import com.mtgcollectionorganizer.api.user.repository.UserPasswordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserPasswordRepository userPasswordRepository;

    @MockitoBean
    private LoginService loginService;

    @Test
    @DisplayName("/v3/api-docs deve ser público")
    void shouldAllowApiDocsWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/swagger-ui não deve exigir autenticação")
    void shouldAllowSwaggerUiWithoutAuthentication() throws Exception {
        final int status = mockMvc.perform(get("/swagger-ui/index.html"))
                .andReturn().getResponse().getStatus();

        assertNotEquals(401, status);
    }

    @Test
    @DisplayName("rota protegida sem token não deve ser acessível")
    void shouldRejectProtectedRouteWithoutToken() throws Exception {
        mockMvc.perform(get("/user/getUserByToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("rota protegida com token válido deve ser acessível")
    void shouldAllowProtectedRouteWithValidToken() throws Exception {
        when(tokenService.getSubject("jwt")).thenReturn("guilherme");
        when(userPasswordRepository.findByUserNameAndIsActive("guilherme"))
                .thenReturn(Optional.of(userPassword()));
        when(loginService.getUsernameByToken("Bearer jwt")).thenReturn("guilherme");

        mockMvc.perform(get("/user/getUserByToken").header("Authorization", "Bearer jwt"))
                .andExpect(status().isCreated())
                .andExpect(content().string("guilherme"));
    }

    @Test
    @DisplayName("POST /user e /user/login devem ser públicos")
    void shouldAllowPublicPosts() throws Exception {
        when(loginService.login("guilherme", "senha123")).thenReturn("jwt");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"guilherme\",\"password\":\"senha123\"}"))
                .andExpect(status().isCreated());
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
