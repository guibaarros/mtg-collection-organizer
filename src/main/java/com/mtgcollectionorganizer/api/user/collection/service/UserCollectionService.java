package com.mtgcollectionorganizer.api.user.collection.service;

import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCollectionDTO;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardTagEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionTagEntity;
import com.mtgcollectionorganizer.api.user.collection.exception.UserCollectionNotFoundException;
import com.mtgcollectionorganizer.api.user.collection.repository.UserCollectionRepository;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserCollectionService {

    private final UserCollectionRepository userCollectionRepository;
    private final UserService userService;
    private final UserCardService userCardService;

    public UserCollectionDTO createCollection(final UserCollectionDTO userCollectionDto, final String username) {
        final UserEntity userEntity = userService.getUserByUsername(username);

        final UserCollectionEntity userCollectionEntity = buildFromDTO(userCollectionDto, userEntity);
        return buildFromEntity(userCollectionRepository.save(userCollectionEntity));
    }

    public void addCardToCollection(final String collectionId,
                                    final String cardId,
                                    final String username) {
        final UserEntity userEntity = userService.getUserByUsername(username);
        final UserCardEntity userCardEntity = userCardService.findById(cardId);

        final UserCollectionEntity userCollectionEntity = findUserCollectionEntityByIdAndUser(
                collectionId,
                userEntity.getId()
        );
        userCollectionEntity.getCards().add(userCardEntity);
        userCollectionRepository.save(userCollectionEntity);
    }

    public void addCardTag(final UserCardTagEntity userCardTag) {
        //TODO adicionar tag à carta
    }

    public void addCollectionTag(final UserCollectionTagEntity userCollectionTag) {
        //TODO adicionar tag à coleção
    }

    public UserCollectionDTO getCollectionById(final String id){
        return buildFromEntity(
                findUserCollectionEntityById(id)
        );
    }

    private UserCollectionEntity findUserCollectionEntityById(String id) {
        return userCollectionRepository.findById(id).orElseThrow(() -> new UserCollectionNotFoundException(id));
    }

    private UserCollectionEntity findUserCollectionEntityByIdAndUser(final String id, final String userId) {
        return userCollectionRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(() -> new UserCollectionNotFoundException(id, userId));
    }

    private UserCollectionDTO buildFromEntity(final UserCollectionEntity userCollectionEntity) {
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

    private UserCollectionEntity buildFromDTO(final UserCollectionDTO userCollectionDto, final UserEntity userEntity) {
        return UserCollectionEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(userCollectionDto.getName())
                .description(userCollectionDto.getDescription())
                .user(userEntity)
//                .tags(userCollectionDto.getTags())
//                .cards(userCollectionDto.getCards())
                .build();
    }
}
