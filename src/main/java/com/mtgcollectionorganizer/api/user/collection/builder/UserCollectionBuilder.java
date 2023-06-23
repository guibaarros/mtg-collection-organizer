package com.mtgcollectionorganizer.api.user.collection.builder;

import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCollectionDTO;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionEntity;
import com.mtgcollectionorganizer.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserCollectionBuilder {

    private UserService userService;

    public UserCollectionDTO buildFromEntity(final UserCollectionEntity userCollectionEntity) {
        return UserCollectionDTO.builder()
                .id(userCollectionEntity.getId())
                .name(userCollectionEntity.getName())
                .description(userCollectionEntity.getDescription())
                .createdAt(userCollectionEntity.getCreatedAt())
                .updatedAt(userCollectionEntity.getUpdatedAt())
//                .tags(userCollectionEntity.getTags())
//                .cards(userCollectionEntity.getCards())
                .build();
    }

    public UserCollectionEntity buildFromDTO(final UserCollectionDTO userCollectionDto) {
        return UserCollectionEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(userCollectionDto.getName())
                .description(userCollectionDto.getDescription())
                .user(userService.getByUserName(userCollectionDto.getUserName())) //TODO ajustar
//                .tags(userCollectionDto.getTags())
//                .cards(userCollectionDto.getCards())
                .build();
    }
}
