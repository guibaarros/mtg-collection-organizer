package com.mtgcollectionorganizer.api.card.type.builder;

import com.mtgcollectionorganizer.api.card.type.builder.exception.InvalidTypeNameException;
import com.mtgcollectionorganizer.api.card.type.entity.TypeEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class TypeEntityBuilderTest {

    @InjectMocks
    private TypeEntityBuilder typeEntityBuilder;

    @ParameterizedTest
    @ArgumentsSource(TypeEntityArgumentProvider.class)
    @DisplayName("Test TypeBuilder.build() with valid values")
    void testBuild(final String name, final TypeEntity expectedTypeEntity) {
        final TypeEntity actualTypeEntity = typeEntityBuilder.build(name);
        assertEquals(expectedTypeEntity, actualTypeEntity);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Test TypeBuilder.build() null and empty values")
    void testBuildWithNameNullAndEmpty(final String name){
        final InvalidTypeNameException invalidTypeNameException = Assertions.assertThrows(InvalidTypeNameException.class, () ->
                typeEntityBuilder.build(name));
        assertEquals("name can not be null or empty", invalidTypeNameException.getMessage());
    }

    static class TypeEntityArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of("Artifact",
                            TypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Artifact")
                                    .build()
                    ),
                    Arguments.of("Land",
                            TypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Land")
                                    .build()
                    ),
                    Arguments.of("Instant",
                            TypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Instant")
                                    .build()
                    ),
                    Arguments.of("Creature",
                            TypeEntity
                                    .builder()
                                    .id(UUID.randomUUID().toString())
                                    .name("Creature")
                                    .build()
                    )
            );
        }
    }
}
