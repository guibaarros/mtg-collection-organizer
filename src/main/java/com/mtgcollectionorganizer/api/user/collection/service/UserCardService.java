package com.mtgcollectionorganizer.api.user.collection.service;

import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.card.service.CardService;
import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCardDTO;
import com.mtgcollectionorganizer.api.user.collection.entity.StateEnum;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardEntity;
import com.mtgcollectionorganizer.api.user.collection.exception.UserCardNotFoundException;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.repository.UserCardRepository;
import com.mtgcollectionorganizer.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserCardService {

    private final UserCardRepository userCardRepository;
    private final CardService cardService;
    private final UserService userService;

    public String addCard(final UserCardDTO userCardDTO, final String username) {
        final UserEntity userEntity = userService.getUserByUsername(username);
        final UserCardEntity userCardEntity = buildFromDTO(userCardDTO, userEntity);

        final Optional<UserCardEntity> userCardEntityByUniqueKey = getUserCardEntityByUniqueKey(
                userCardEntity.getCard().getId(),
                userEntity.getId(),
                userCardEntity.getIsFoil(),
                userCardEntity.getIsPromo(),
                userCardEntity.getState()
        );

        if (userCardEntityByUniqueKey.isPresent()) {
            userCardEntityByUniqueKey.get().addQuantity(userCardEntity.getQuantity());
            return userCardRepository.save(userCardEntityByUniqueKey.get()).getId();
        }
        return userCardRepository.save(userCardEntity).getId();
    }

    private Optional<UserCardEntity> getUserCardEntityByUniqueKey(final String cardId,
                                                                  final String userId,
                                                                  final Boolean isFoil,
                                                                  final Boolean isPromo,
                                                                  final StateEnum state) {
        return userCardRepository.findByUniqueKey(
                cardId,
                userId,
                isFoil,
                isPromo,
                state
        );
    }

    private UserCardEntity buildFromDTO(final UserCardDTO userCardDTO, final UserEntity userEntity) {
        final CardEntity cardEntity = cardService.getBySetCodeAndCollectorNumber(
                userCardDTO.getCard().getSetCode(),
                userCardDTO.getCard().getCollectorNumber(),
                userCardDTO.getCard().getLanguage()
        );

        return UserCardEntity.builder()
                .isFoil(userCardDTO.getIsFoil())
                .isPromo(userCardDTO.getIsPromo())
                .state(userCardDTO.getState())
//                .extras(userCardDTO.getExtras()) // TODO adicionar extras
                .quantity(userCardDTO.getQuantity())
                .user(userEntity)
                .card(cardEntity)
//                .tags() // TODO adicionar tags
                .build();
    }

    public UserCardEntity findById(final String id) {
        return userCardRepository.findById(id).orElseThrow(() -> new UserCardNotFoundException(id));
    }
}
