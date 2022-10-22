package com.mtgcollectionorganizer.api.card.type.service;

import com.mtgcollectionorganizer.api.card.type.builder.TypeEntityBuilder;
import com.mtgcollectionorganizer.api.card.type.entity.TypeEntity;
import com.mtgcollectionorganizer.api.card.type.repository.TypeEntityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TypeServiceTest {

    @Mock
    private TypeEntityBuilder typeEntityBuilder;

    @Mock
    private TypeEntityRepository typeEntityRepository;

    @InjectMocks
    private TypeService typeService;

    @ParameterizedTest
    @ArgumentsSource(TypeEntityListArgumentProvider.class)
    @DisplayName("Test TypeService.getTypesFromCardDTOTypeLine() with valid values")
    void testGetTypesFromCardDTOTypeLine(final String typeLine, final List<TypeEntity> expectedTypeEntityList){
        when(typeEntityBuilder.build(anyString()))
                .thenAnswer(a ->
                        TypeEntity.builder()
                                .id(UUID.randomUUID().toString())
                                .name((String) a.getArguments()[0])
                                .build()
                );

        when(typeEntityRepository.findByName(anyString())).thenReturn(Optional.empty());

        final List<TypeEntity> actualTypeEntityList = typeService.getTypesFromCardDTOTypeLine(typeLine);

        assertArrayEquals(expectedTypeEntityList.toArray(), actualTypeEntityList.toArray());

        verify(typeEntityBuilder, times(expectedTypeEntityList.size())).build(anyString());
        verify(typeEntityRepository, times(expectedTypeEntityList.size())).findByName(anyString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Test TypeService.getTypesFromCardDTOTypeLine() with null and empty values")
    void testGetTypesFromCardDTOTypeLineWithNullAndEmptyValues(final String typeLine){
        final List<TypeEntity> actualTypeEntityList = typeService.getTypesFromCardDTOTypeLine(typeLine);
        assertArrayEquals(Collections.emptyList().toArray(), actualTypeEntityList.toArray());
        verify(typeEntityBuilder, times(0)).build(anyString());
        verify(typeEntityRepository, times(0)).findByName(anyString());
    }

    static class TypeEntityListArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of("Artifact Land",
                            Arrays.asList(
                                    TypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Artifact")
                                            .build(),
                                    TypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Land")
                                            .build()
                            )
                    ),
                    Arguments.of("Land",
                            Collections.singletonList(TypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Land")
                                    .build())
                    ),
                    Arguments.of("Artifact Creature — Construct",
                            Arrays.asList(
                                    TypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Artifact")
                                            .build(),
                                    TypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Creature")
                                            .build()
                            )
                    ),
                    Arguments.of("Legendary Planeswalker — Liliana",
                            Arrays.asList(
                                    TypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Legendary")
                                            .build(),
                                    TypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Planeswalker")
                                            .build()
                            )
                    )
            );
        }
    }
}
