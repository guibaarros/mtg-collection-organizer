package com.mtgcollectionorganizer.api.user.collection.service;

import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCollectionDTO;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionEntity;
import com.mtgcollectionorganizer.api.user.collection.exception.UserCollectionNotFoundException;
import com.mtgcollectionorganizer.api.user.collection.repository.UserCollectionRepository;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import com.mtgcollectionorganizer.api.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCollectionServiceTest {

    @Mock
    private UserCollectionRepository userCollectionRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserCardService userCardService;

    @InjectMocks
    private UserCollectionService userCollectionService;

    @Test
    @DisplayName("createCollection deve salvar a coleção com o usuário autenticado")
    void shouldCreateCollection() {
        final UserEntity user = user();
        final UserCollectionDTO dto = UserCollectionDTO.builder()
                .name("Meu deck")
                .description("Deck moderno")
                .build();

        when(userService.getUserByUsername("guilherme")).thenReturn(user);
        when(userCollectionRepository.save(any(UserCollectionEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        final UserCollectionDTO result = userCollectionService.createCollection(dto, "guilherme");

        assertEquals("Meu deck", result.getName());
        assertEquals("Deck moderno", result.getDescription());
    }

    @Test
    @DisplayName("addCardToCollection deve adicionar a carta à coleção do próprio usuário")
    void shouldAddCardToOwnCollection() {
        final UserEntity user = user();
        final UserCardEntity card = UserCardEntity.builder().id("card-id").build();
        final UserCollectionEntity collection = collection(user);

        when(userService.getUserByUsername("guilherme")).thenReturn(user);
        when(userCardService.findById("card-id")).thenReturn(card);
        when(userCollectionRepository.findByIdAndUserId("collection-id", "user-id"))
                .thenReturn(Optional.of(collection));

        userCollectionService.addCardToCollection("collection-id", "card-id", "guilherme");

        assertTrue(collection.getCards().contains(card));
        verify(userCollectionRepository).save(collection);
    }

    @Test
    @DisplayName("addCardToCollection deve rejeitar coleção de outro usuário")
    void shouldRejectCollectionFromAnotherUser() {
        when(userService.getUserByUsername("outro")).thenReturn(
                UserEntity.builder().id("outro-id").userName("outro").build());
        when(userCardService.findById("card-id")).thenReturn(UserCardEntity.builder().id("card-id").build());
        when(userCollectionRepository.findByIdAndUserId("collection-id", "outro-id"))
                .thenReturn(Optional.empty());

        assertThrows(UserCollectionNotFoundException.class,
                () -> userCollectionService.addCardToCollection("collection-id", "card-id", "outro"));

        verify(userCollectionRepository, never()).save(any());
    }

    @Test
    @DisplayName("getCollectionById deve retornar a coleção encontrada")
    void shouldGetCollectionById() {
        when(userCollectionRepository.findById("collection-id"))
                .thenReturn(Optional.of(collection(user())));

        assertEquals("Meu deck", userCollectionService.getCollectionById("collection-id").getName());
    }

    @Test
    @DisplayName("getCollectionById deve lançar UserCollectionNotFoundException quando ausente")
    void shouldThrowWhenCollectionNotFound() {
        when(userCollectionRepository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(UserCollectionNotFoundException.class,
                () -> userCollectionService.getCollectionById("inexistente"));
    }

    private static UserEntity user() {
        return UserEntity.builder().id("user-id").userName("guilherme").build();
    }

    private static UserCollectionEntity collection(final UserEntity user) {
        return UserCollectionEntity.builder()
                .id("collection-id")
                .name("Meu deck")
                .user(user)
                .cards(new ArrayList<>())
                .build();
    }
}
