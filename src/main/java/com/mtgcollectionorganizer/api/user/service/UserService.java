package com.mtgcollectionorganizer.api.user.service;

import com.mtgcollectionorganizer.api.user.builder.UserBuilder;
import com.mtgcollectionorganizer.api.user.builder.UserPasswordBuilder;
import com.mtgcollectionorganizer.api.user.controller.dto.UserDTO;
import com.mtgcollectionorganizer.api.user.controller.dto.UserPasswordDTO;
import com.mtgcollectionorganizer.api.user.entity.RoleEntity;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import com.mtgcollectionorganizer.api.user.exception.UserAlreadyExistsException;
import com.mtgcollectionorganizer.api.user.exception.UserNotFoundException;
import com.mtgcollectionorganizer.api.user.exception.UserPasswordNotFoundException;
import com.mtgcollectionorganizer.api.user.repository.UserPasswordRepository;
import com.mtgcollectionorganizer.api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;
    private final UserBuilder userBuilder;
    private final UserPasswordBuilder userPasswordBuilder;

    public void addRole(final RoleEntity role) {
        // TODO cadastrar perfil do usuario
    }

    @Transactional
    public String addUser(final UserDTO userDto) {
        validateUserAlreadyExists(userDto);
        final UserPasswordEntity userPassword = userPasswordBuilder.build(
                userDto.getPassword(),
                userDto.getUserName(),
                userBuilder.buildFromDTO(userDto));
        final UserPasswordEntity persistedUser = userPasswordRepository.save(userPassword);
        return persistedUser.getUser().getId();
    }

    private void validateUserAlreadyExists(final UserDTO userDto) {
        if (findUserByUserName(userDto.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException(userDto.getUserName());
        }
    }

    @Transactional
    public void changePassword(final String username, final UserPasswordDTO userPasswordDto) {
        final UserPasswordEntity userPassword = findUserPasswordByUserName(username)
                .orElseThrow(() -> new UserPasswordNotFoundException(username));
        userPassword.disable();
        userPasswordRepository.save(userPassword);

        final UserPasswordEntity newUserPassword =userPasswordBuilder.build(
                userPasswordDto.getPassword(),
                username,
                userPassword.getUser());
        userPasswordRepository.save(newUserPassword);
    }

    public UserPasswordEntity getUserPasswordByUserName(final String userName) {
        return findUserPasswordByUserName(userName).orElseThrow(() -> new UserNotFoundException("userName", userName));
    }

    public UserEntity getUserByUsername(final String username) {
        return findUserByUserName(username).orElseThrow(() -> new UserNotFoundException("username", username));
    }

    private Optional<UserEntity> findUserByUserName(final String userName) {
        return userRepository.findByUserName(userName);
    }

    private Optional<UserPasswordEntity> findUserPasswordByUserName(final String userName) {
        return userPasswordRepository.findByUserNameAndIsActive(userName);
    }
}
