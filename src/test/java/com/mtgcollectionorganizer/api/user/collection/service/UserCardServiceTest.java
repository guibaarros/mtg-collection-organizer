package com.mtgcollectionorganizer.api.user.collection.service;

import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.card.service.CardService;
import com.mtgcollectionorganizer.api.user.collection.controller.dto.CardDTO;
import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCardDTO;
import com.mtgcollectionorganizer.api.user.collection.entity.LanguageEnum;
import com.mtgcollectionorganizer.api.user.collection.entity.StateEnum;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardEntity;
import com.mtgcollectionorganizer.api.user.collection.exception.UserCardNotFoundException;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.repository.UserCardRepository;
import com.mtgcollectionorganizer.api.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCardServiceTest {

    @Mock
    private UserCardRepository userCardRepository;

    @Mock
    private CardService cardService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserCardService userCardService;

    @Test
    @DisplayName("primeira carta do usuário deve ser salva como nova entidade")
    void shouldSaveNewUserCard() {
        final UserEntity user = user();
        final CardEntity card = CardEntity.builder().id("card-id").build();

        when(userService.getUserByUsername("guilherme")).thenReturn(user);
        when(cardService.getBySetCodeAndCollectorNumber("vow", 149, LanguageEnum.EN)).thenReturn(card);
        when(userCardRepository.findByUniqueKey("card-id", "user-id", false, false, StateEnum.MINT))
                .thenReturn(Optional.empty());
        when(userCardRepository.save(any(UserCardEntity.class))).thenAnswer(invocation -> {
            final UserCardEntity saved = invocation.getArgument(0);
            return UserCardEntity.builder()
                    .id("new-card-id")
                    .isFoil(saved.getIsFoil())
                    .isPromo(saved.getIsPromo())
                    .state(saved.getState())
                    .quantity(saved.getQuantity())
                    .user(saved.getUser())
                    .card(saved.getCard())
                    .build();
        });

        assertEquals("new-card-id", userCardService.addCard(userCardDTO(2), "guilherme"));
    }

    @Test
    @DisplayName("carta já existente deve somar a quantidade em vez de criar outra")
    void shouldSumQuantityWhenUniqueKeyAlreadyExists() {
        final UserEntity user = user();
        final CardEntity card = CardEntity.builder().id("card-id").build();
        final UserCardEntity existing = UserCardEntity.builder()
                .id("existing-id")
                .quantity(2)
                .user(user)
                .card(card)
                .build();

        when(userService.getUserByUsername("guilherme")).thenReturn(user);
        when(cardService.getBySetCodeAndCollectorNumber("vow", 149, LanguageEnum.EN)).thenReturn(card);
        when(userCardRepository.findByUniqueKey("card-id", "user-id", false, false, StateEnum.MINT))
                .thenReturn(Optional.of(existing));
        when(userCardRepository.save(existing)).thenReturn(existing);

        assertEquals("existing-id", userCardService.addCard(userCardDTO(3), "guilherme"));
        assertEquals(5, existing.getQuantity());
        verify(userCardRepository).save(existing);
    }

    @Test
    @DisplayName("findById deve lançar UserCardNotFoundException quando não encontrado")
    void shouldThrowWhenUserCardNotFound() {
        when(userCardRepository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(UserCardNotFoundException.class, () -> userCardService.findById("inexistente"));
        verify(userCardRepository, never()).save(any());
    }

    private static UserEntity user() {
        return UserEntity.builder().id("user-id").userName("guilherme").build();
    }

    private static UserCardDTO userCardDTO(final Integer quantity) {
        final CardDTO card = new CardDTO();
        card.setSetCode("vow");
        card.setCollectorNumber(149);
        card.setLanguage(LanguageEnum.EN);

        return UserCardDTO.builder()
                .card(card)
                .isFoil(false)
                .isPromo(false)
                .state(StateEnum.MINT)
                .quantity(quantity)
                .build();
    }
}
