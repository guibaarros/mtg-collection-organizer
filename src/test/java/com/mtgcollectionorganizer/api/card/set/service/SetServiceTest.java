package com.mtgcollectionorganizer.api.card.set.service;


import com.mtgcollectionorganizer.api.card.set.builder.SetEntityBuilder;
import com.mtgcollectionorganizer.api.card.set.entity.SetEntity;
import com.mtgcollectionorganizer.api.card.set.repository.SetEntityRepository;
import com.mtgcollectionorganizer.api.scryfall.set.dto.ScryfallSetDTO;
import com.mtgcollectionorganizer.api.scryfall.set.service.ScryfallSetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SetServiceTest {

    @Mock
    private SetEntityBuilder setEntityBuilder;

    @Mock
    private SetEntityRepository setEntityRepository;

    @Mock
    private ScryfallSetService scryfallSetService;

    @InjectMocks
    private SetService setService;

    @Test
    @DisplayName("Test SetSevice.buildSetEntityByScryfallData()")
    void testBuildSetEntityByScryfallData(){
        final String setCode = "vow";

        final ScryfallSetDTO setDTO = getScryfallSetDTO();

        final SetEntity expectedSetEntity = getSetEntity(setCode);

        when(setEntityBuilder.build(eq(setDTO))).thenReturn(expectedSetEntity);

        final SetEntity actualSetEntity = setService.buildSetEntityByScryfallData(setDTO);

        Assertions.assertEquals(expectedSetEntity, actualSetEntity);
    }

    @Test
    @DisplayName("Test SetService.getSetByCode() with persisted Set")
    void testGetSetByCodeWithPersistedSet(){
        final String setCode = "vow";

        final SetEntity expectedSetEntity = getSetEntity(setCode);

        when(setEntityRepository.findByCode(eq(setCode))).thenReturn(Optional.of(expectedSetEntity));

        final SetEntity actualSetEntity = setService.getSetByCode(setCode);

        Assertions.assertEquals(expectedSetEntity, actualSetEntity);

    }

    @Test
    @DisplayName("Test SetService.getSetByCode() with not persisted Set")
    void testGetSetByCodeWithNotPersistedSet(){
        final String setCode = "vow";

        final ScryfallSetDTO setDTO = getScryfallSetDTO();

        final SetEntity expectedSetEntity = getSetEntity(setCode);

        when(setEntityRepository.findByCode(eq(setCode))).thenReturn(Optional.of(expectedSetEntity));
        when(scryfallSetService.getSetByCode(eq(setCode))).thenReturn(setDTO);
        when(setEntityBuilder.build(eq(setDTO))).thenReturn(expectedSetEntity);

        final SetEntity actualSetEntity = setService.getSetByCode(setCode);

        Assertions.assertEquals(expectedSetEntity, actualSetEntity);
    }

    private static SetEntity getSetEntity(final String setCode) {
        return SetEntity
                .builder()
                .id("8144b676-569f-4716-8005-bc8f0778f3fa")
                .cardCount(417)
                .code(setCode)
                .name("Innistrad: Crimson Vow")
                .localizedName("Innistrad: Crimson Vow")
                .iconUri("https://svgs.scryfall.io/sets/vow.svg?1669611600")
                .scryfallUri("https://scryfall.com/sets/vow")
                .build();
    }

    private static ScryfallSetDTO getScryfallSetDTO() {
        return ScryfallSetDTO.builder()
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
    }

}
