package com.mtgcollectionorganizer.api.card.set.builder;

import com.mtgcollectionorganizer.api.card.set.entity.SetEntity;
import com.mtgcollectionorganizer.api.scryfall.set.dto.ScryfallSetDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SetEntityBuilderTest {

    @InjectMocks
    private SetEntityBuilder setEntityBuilder;

    @Test
    @DisplayName("Test SetEntityBuilder.build()")
    void testBuild(){
        final String setCode = "vow";

        final SetEntity expectedSetEntity = SetEntity
                .builder()
                .id("8144b676-569f-4716-8005-bc8f0778f3fa")
                .cardCount(417)
                .code(setCode)
                .name("Innistrad: Crimson Vow")
                .localizedName("Innistrad: Crimson Vow")
                .iconUri("https://svgs.scryfall.io/sets/vow.svg?1669611600")
                .scryfallUri("https://scryfall.com/sets/vow")
                .build();

        final ScryfallSetDTO setDTO = ScryfallSetDTO.builder()
                .object("set")
                .id("8144b676-569f-4716-8005-bc8f0778f3fa")
                .code("vow")
                .mtgoCode("vow")
                .arenaCode("vow")
                .tcgplayerId(2862)
                .name("Innistrad: Crimson Vow")
                .uri("https://api.scryfall.com/sets/8144b676-569f-4716-8005-bc8f0778f3fa")
                .scryfallUri("https://scryfall.com/sets/vow")
                .searchUri("https://api.scryfall.com/cards/search?include_extras=true&include_variations=true&order=set&q=e%3Avow&unique=prints")
                .releasedAt("2021-11-19")
                .setType("expansion")
                .cardCount(417)
                .printedsize(277)
                .digital(false)
                .nonfoilOnly(false)
                .foilOnly(false)
                .blockCode("dbl")
                .block("Innistrad: Double Feature")
                .iconSvgUri("https://svgs.scryfall.io/sets/vow.svg?1669611600")
                .build();

        final SetEntity actualSetEntity = setEntityBuilder.build(setDTO);

        Assertions.assertEquals(expectedSetEntity, actualSetEntity);
    }
}
