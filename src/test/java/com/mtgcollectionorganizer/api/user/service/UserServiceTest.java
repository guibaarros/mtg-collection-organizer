package com.mtgcollectionorganizer.api.user.service;

import com.mtgcollectionorganizer.api.user.builder.UserBuilder;
import com.mtgcollectionorganizer.api.user.builder.UserPasswordBuilder;
import com.mtgcollectionorganizer.api.user.controller.dto.UserDTO;
import com.mtgcollectionorganizer.api.user.controller.dto.UserPasswordDTO;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import com.mtgcollectionorganizer.api.user.exception.UserAlreadyExistsException;
import com.mtgcollectionorganizer.api.user.exception.UserNotFoundException;
import com.mtgcollectionorganizer.api.user.exception.UserPasswordNotFoundException;
import com.mtgcollectionorganizer.api.user.repository.UserPasswordRepository;
import com.mtgcollectionorganizer.api.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPasswordRepository userPasswordRepository;

    @Mock
    private UserBuilder userBuilder;

    @Mock
    private UserPasswordBuilder userPasswordBuilder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("addUser deve salvar e retornar o id do usuário")
    void shouldAddUser() {
        final UserDTO dto = mock(UserDTO.class);
        final UserEntity user = UserEntity.builder().id("user-id").build();
        final UserPasswordEntity userPassword = UserPasswordEntity.builder().user(user).build();

        when(dto.getUserName()).thenReturn("guilherme");
        when(dto.getPassword()).thenReturn("senha123");
        when(userRepository.findByUserName("guilherme")).thenReturn(Optional.empty());
        when(userBuilder.buildFromDTO(dto)).thenReturn(user);
        when(userPasswordBuilder.build("senha123", user)).thenReturn(userPassword);
        when(userPasswordRepository.save(userPassword)).thenReturn(userPassword);

        assertEquals("user-id", userService.addUser(dto));
    }

    @Test
    @DisplayName("addUser com username duplicado deve lançar UserAlreadyExistsException")
    void shouldRejectDuplicatedUsername() {
        final UserDTO dto = mock(UserDTO.class);
        when(dto.getUserName()).thenReturn("guilherme");
        when(userRepository.findByUserName("guilherme"))
                .thenReturn(Optional.of(UserEntity.builder().id("user-id").build()));

        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(dto));

        verifyNoInteractions(userPasswordRepository, userBuilder, userPasswordBuilder);
    }

    @Test
    @DisplayName("changePassword deve desabilitar a senha antiga e salvar a nova")
    void shouldDisableOldPasswordAndSaveNewOne() {
        final UserDTO dto = mock(UserDTO.class);
        final UserPasswordDTO newPasswordDto = mock(UserPasswordDTO.class);
        final UserEntity user = UserEntity.builder().id("user-id").build();
        final UserPasswordEntity oldPassword = UserPasswordEntity.builder()
                .encryptedPassword("antiga")
                .active(true)
                .user(user)
                .build();
        final UserPasswordEntity newPassword = UserPasswordEntity.builder()
                .encryptedPassword("nova")
                .active(true)
                .user(user)
                .build();

        when(userPasswordRepository.findByUserNameAndIsActive("guilherme"))
                .thenReturn(Optional.of(oldPassword));
        when(newPasswordDto.getPassword()).thenReturn("nova");
        when(userPasswordBuilder.build("nova", user)).thenReturn(newPassword);

        userService.changePassword("guilherme", newPasswordDto);

        final ArgumentCaptor<UserPasswordEntity> captor = ArgumentCaptor.forClass(UserPasswordEntity.class);
        verify(userPasswordRepository, times(2)).save(captor.capture());
        assertFalse(captor.getAllValues().get(0).isEnabled());
        assertEquals("nova", captor.getAllValues().get(1).getPassword());
    }

    @Test
    @DisplayName("changePassword sem senha ativa deve lançar UserPasswordNotFoundException")
    void shouldFailWhenNoActivePassword() {
        final UserPasswordDTO dto = mock(UserPasswordDTO.class);
        when(userPasswordRepository.findByUserNameAndIsActive("guilherme")).thenReturn(Optional.empty());

        assertThrows(UserPasswordNotFoundException.class,
                () -> userService.changePassword("guilherme", dto));

        verify(userPasswordRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("getUserPasswordByUserName deve lançar UserNotFoundException quando ausente")
    void shouldThrowWhenUserPasswordNotFound() {
        when(userPasswordRepository.findByUserNameAndIsActive("inexistente")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserPasswordByUserName("inexistente"));
    }

    @Test
    @DisplayName("getUserByUsername deve retornar o usuário encontrado")
    void shouldGetUserByUsername() {
        final UserEntity user = UserEntity.builder().id("user-id").userName("guilherme").build();
        when(userRepository.findByUserName("guilherme")).thenReturn(Optional.of(user));

        assertEquals(user, userService.getUserByUsername("guilherme"));
    }
}
