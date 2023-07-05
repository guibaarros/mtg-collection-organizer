package com.mtgcollectionorganizer.api.user.builder;

import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserPasswordBuilder {

    private final PasswordEncoder passwordEncoder;

    public UserPasswordEntity build(final String password, final String username, final UserEntity userEntity) {
        return UserPasswordEntity.builder()
                .id(UUID.randomUUID().toString())
                .encryptedPassword(passwordEncoder.encode(username.concat(password)))
                .user(userEntity)
                .active(Boolean.TRUE)
                .build();
    }
}
