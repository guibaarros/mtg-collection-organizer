package com.mtgcollectionorganizer.api.card.subtype.builder;

import com.mtgcollectionorganizer.api.card.subtype.builder.exception.InvalidSubtypeNameException;
import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
class SubtypeEntityBuilderTest {

    @InjectMocks
    private SubtypeEntityBuilder subtypeEntityBuilder;

    @ParameterizedTest
    @ArgumentsSource(SubtypeEntityArgumentProvider.class)
    @DisplayName("Test SubtypeBuilder.build() with valid values")
    void testBuild(final String name, final SubtypeEntity expectedTypeEntity) {
        final SubtypeEntity actualTypeEntity = subtypeEntityBuilder.build(name);
        Assertions.assertEquals(expectedTypeEntity, actualTypeEntity);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Test SubtypeBuilder.build() null and empty values")
    void testBuildWithNameNullAndEmpty(final String name){
        final InvalidSubtypeNameException invalidSubtypeNameException = Assertions.assertThrows(InvalidSubtypeNameException.class, () ->
                subtypeEntityBuilder.build(name));
        Assertions.assertEquals("name can not be null or empty", invalidSubtypeNameException.getMessage());
    }

    static class SubtypeEntityArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of("Equipment",
                            SubtypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Equipment")
                                    .build()
                    ),
                    Arguments.of("Aura",
                            SubtypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Aura")
                                    .build()
                    ),
                    Arguments.of("Goblin",
                            SubtypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Goblin")
                                    .build()
                    ),
                    Arguments.of("Curse",
                            SubtypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Curse")
                                    .build()
                    )
            );
        }
    }
}
