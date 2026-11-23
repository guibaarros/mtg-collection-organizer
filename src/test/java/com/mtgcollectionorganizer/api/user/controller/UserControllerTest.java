package com.mtgcollectionorganizer.api.user.controller;

import com.mtgcollectionorganizer.api.config.exceptionhandler.ControllerExceptionHandler;
import com.mtgcollectionorganizer.api.security.LoginService;
import com.mtgcollectionorganizer.api.user.controller.dto.UserDTO;
import com.mtgcollectionorganizer.api.user.exception.UserAlreadyExistsException;
import com.mtgcollectionorganizer.api.user.exception.UserNotAuthorizedException;
import com.mtgcollectionorganizer.api.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private LoginService loginService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userService, loginService))
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("login deve repassar usuário e senha recebidos ao LoginService")
    void shouldPassCredentialsToLoginService() throws Exception {
        when(loginService.login("guilherme", "senha123")).thenReturn("jwt");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"guilherme\",\"password\":\"senha123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("jwt"));

        verify(loginService).login("guilherme", "senha123");
    }

    @Test
    @DisplayName("createUser deve retornar 201 com o id do usuário")
    void shouldCreateUser() throws Exception {
        when(userService.addUser(any(UserDTO.class))).thenReturn("user-id");

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"guilherme\",\"password\":\"senha123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("user-id"));
    }

    @Test
    @DisplayName("createUser com usuário existente deve retornar 422")
    void shouldReturnUnprocessableEntityForDuplicatedUser() throws Exception {
        when(userService.addUser(any(UserDTO.class)))
                .thenThrow(new UserAlreadyExistsException("guilherme"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"guilherme\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$[0].message").exists());
    }

    @Test
    @DisplayName("login com credenciais inválidas deve retornar 401")
    void shouldReturnUnauthorizedForInvalidCredentials() throws Exception {
        when(loginService.login(any(), any()))
                .thenThrow(new UserNotAuthorizedException("guilherme"));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"guilherme\",\"password\":\"errada\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$[0].message").exists());
    }

    @Test
    @DisplayName("changePassword deve usar o usuário do token")
    void shouldChangePasswordUsingTokenUsername() throws Exception {
        when(loginService.getUsernameByToken("Bearer jwt")).thenReturn("guilherme");

        mockMvc.perform(patch("/user/password")
                        .header("Authorization", "Bearer jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"nova-senha\"}"))
                .andExpect(status().isCreated());

        verify(userService).changePassword(eq("guilherme"), any());
    }
}
