package com.mtgcollectionorganizer.api.user.builder;

import com.mtgcollectionorganizer.api.user.controller.dto.UserDTO;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserBuilder {
    public UserEntity buildFromDTO(final UserDTO userDto){
        return UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .displayName(userDto.getDisplayName())
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .roles(Collections.emptyList()) //TODO ajustar roles
                .build();
    }
}
