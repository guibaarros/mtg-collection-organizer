package com.mtgcollectionorganizer.api.user.collection.service;

import com.mtgcollectionorganizer.api.user.collection.builder.UserCollectionBuilder;
import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCollectionDTO;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardTagEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionTagEntity;
import com.mtgcollectionorganizer.api.user.collection.exception.UserCollectionNotFoundException;
import com.mtgcollectionorganizer.api.user.collection.repository.UserCollectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCollectionService {

    final UserCollectionRepository userCollectionRepository;
    final UserCollectionBuilder userCollectionBuilder;

    public void createCollection(final UserCollectionDTO userCollectionDto) {
        final UserCollectionEntity userCollectionEntity = userCollectionBuilder.buildFromDTO(userCollectionDto);
        userCollectionRepository.save(userCollectionEntity);

        //TODO criação e persistencia da coleção do usuário
    }

    public void addCardToCollection(final UserCardEntity userCard, final UserCollectionEntity userCollection) {
        //TODO adicionar carta à coleção
    }

    public void addCardTag(final UserCardTagEntity userCardTag) {
        //TODO adicionar tag à carta
    }

    public void addCollectionTag(final UserCollectionTagEntity userCollectionTag) {
        //TODO adicionar tag à coleção
    }

    public UserCollectionDTO getCollectionById(final String id){
        return userCollectionBuilder.buildFromEntity(
                userCollectionRepository.findById(id).orElseThrow(() -> new UserCollectionNotFoundException(id))
        );
    }
}
