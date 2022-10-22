package com.mtgcollectionorganizer.api.card.subtype.service;

import com.mtgcollectionorganizer.api.card.subtype.builder.SubtypeEntityBuilder;
import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import com.mtgcollectionorganizer.api.card.subtype.repository.SubtypeEntityRepository;
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
public class SubtypeServiceTest {

    @Mock
    private SubtypeEntityBuilder subtypeEntityBuilder;

    @Mock
    private SubtypeEntityRepository subtypeEntityRepository;

    @InjectMocks
    private SubtypeService subtypeService;

    @ParameterizedTest
    @ArgumentsSource(SubtypeEntityListArgumentProvider.class)
    @DisplayName("Test SubtypeService.getSubtypesFromCardDTOTypeLine() with valid values")
    void testGetSubtypesFromCardDTOTypeLine(final String typeLine, final List<SubtypeEntity> expectedSubtypeEntityList){
        when(subtypeEntityBuilder.build(anyString()))
                .thenAnswer(a ->
                        SubtypeEntity.builder()
                                .id(UUID.randomUUID().toString())
                                .name((String) a.getArguments()[0])
                                .build()
                );

        when(subtypeEntityRepository.findByName(anyString())).thenReturn(Optional.empty());

        final List<SubtypeEntity> actualSubtypeEntityList = subtypeService.getSubtypesFromCardDTOTypeLine(typeLine);

        assertArrayEquals(expectedSubtypeEntityList.toArray(), actualSubtypeEntityList.toArray());

        verify(subtypeEntityBuilder, times(expectedSubtypeEntityList.size())).build(anyString());
        verify(subtypeEntityRepository, times(expectedSubtypeEntityList.size())).findByName(anyString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Test SubtypeService.getSubtypesFromCardDTOTypeLine() with null and empty values")
    void testGetSubtypesFromCardDTOTypeLineWithNullAndEmptyValues(final String typeLine){
        final List<SubtypeEntity> actualSubtypeEntityList = subtypeService.getSubtypesFromCardDTOTypeLine(typeLine);
        assertArrayEquals(Collections.emptyList().toArray(), actualSubtypeEntityList.toArray());
        verify(subtypeEntityBuilder, times(0)).build(anyString());
        verify(subtypeEntityRepository, times(0)).findByName(anyString());
    }

    static class SubtypeEntityListArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of("Artifact Land",
                            Collections.emptyList()
                    ),
                    Arguments.of("Artifact Creature — Construct",
                            Collections.singletonList(
                                    SubtypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Construct")
                                            .build()
                            )
                    ),
                    Arguments.of("Enchantment — Aura",
                            Collections.singletonList(
                                    SubtypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Aura")
                                            .build()
                            )
                    ),
                    Arguments.of("Artifact Creature — Assembly-Worker",
                            Collections.singletonList(
                                    SubtypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Assembly-Worker")
                                            .build()
                            )
                    ),
                    Arguments.of("Legendary Creature — Human Soldier",
                            Arrays.asList(
                                    SubtypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Human")
                                            .build(),
                                    SubtypeEntity
                                            .builder()
                                            .id(UUID.randomUUID().toString())
                                            .name("Soldier")
                                            .build()
                            )
                    )
            );
        }
    }
}
