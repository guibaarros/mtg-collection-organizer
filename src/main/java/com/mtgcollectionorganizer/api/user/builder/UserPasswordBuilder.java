package com.mtgcollectionorganizer.api.user.builder;

import com.mtgcollectionorganizer.api.tools.HashTool;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserPasswordBuilder {
    public UserPasswordEntity build(final String password, final String username, final UserEntity userEntity) {
        return UserPasswordEntity.builder()
                .id(UUID.randomUUID().toString())
                .encryptedPassword(HashTool.hashSHA256(username.concat(password)))
                .user(userEntity)
                .active(Boolean.TRUE)
                .build();
    }
}
