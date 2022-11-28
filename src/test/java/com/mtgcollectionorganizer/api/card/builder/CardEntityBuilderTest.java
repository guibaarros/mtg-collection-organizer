package com.mtgcollectionorganizer.api.card.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.card.entity.RarityEnum;
import com.mtgcollectionorganizer.api.card.set.entity.SetEntity;
import com.mtgcollectionorganizer.api.card.set.service.SetService;
import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import com.mtgcollectionorganizer.api.card.subtype.service.SubtypeService;
import com.mtgcollectionorganizer.api.card.type.entity.TypeEntity;
import com.mtgcollectionorganizer.api.card.type.service.TypeService;
import com.mtgcollectionorganizer.api.scryfall.card.dto.ScryfallCardDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CardEntityBuilderTest {

    @Mock
    private SetService setService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TypeService typeService;

    @Mock
    private SubtypeService subtypeService;

    @InjectMocks
    private CardEntityBuilder cardEntityBuilder;

    @Test
    @DisplayName("Test CardEntityBuilder.build()")
    void testBuild() throws JsonProcessingException {
        final String setCode = "vow";
        final SetEntity expectedSetEntity = SetEntity
                .builder()
                .id("8144b676-569f-4716-8005-bc8f0778f3fa")
                .cardCount(417)
                .code(setCode)
                .name("Innistrad: Crimson Vow")
                .iconUri("https://svgs.scryfall.io/sets/vow.svg?1666584000")
                .scryfallUri("https://scryfall.com/sets/vow")
                .build();

        final List<TypeEntity> expectedTypeEntityList = List.of(
                TypeEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Legendary")
                        .build(),
                TypeEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Planeswalker")
                        .build()
        );

        final List<SubtypeEntity> expectedSubtypeEntityList = List.of(
                SubtypeEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Chandra")
                        .build()
        );

        final String expectedImageUris = """
                "image_uris": {
                    "small": "https://cards.scryfall.io/small/front/6/8/681f7c73-92c6-47ba-af56-3ff032ac12da.jpg?1643596175",
                    "normal": "https://cards.scryfall.io/normal/front/6/8/681f7c73-92c6-47ba-af56-3ff032ac12da.jpg?1643596175",
                    "large": "https://cards.scryfall.io/large/front/6/8/681f7c73-92c6-47ba-af56-3ff032ac12da.jpg?1643596175",
                    "png": "https://cards.scryfall.io/png/front/6/8/681f7c73-92c6-47ba-af56-3ff032ac12da.png?1643596175",
                    "art_crop": "https://cards.scryfall.io/art_crop/front/6/8/681f7c73-92c6-47ba-af56-3ff032ac12da.jpg?1643596175",
                    "border_crop": "https://cards.scryfall.io/border_crop/front/6/8/681f7c73-92c6-47ba-af56-3ff032ac12da.jpg?1643596175"
                  }""";


        final ScryfallCardDTO cardDTO = ScryfallCardDTO
                .builder()
                .id("681f7c73-92c6-47ba-af56-3ff032ac12da")
                .set("vow")
                .collectorNumber("149")
                .name("Chandra, Dressed to Kill")
                .printedName("Chandra, Dressed to Kill")
                .manaCost("{1}{R}{R}")
                .cmc(3)
                .imageUris(Map.of(
                        "small", "https://cards.scryfall.io/small/front/9/a/9a1e5598-9881-418d-ba18-f4281f52ecbb.jpg?1646143231",
                        "normal", "https://cards.scryfall.io/normal/front/9/a/9a1e5598-9881-418d-ba18-f4281f52ecbb.jpg?1646143231",
                        "large", "https://cards.scryfall.io/large/front/9/a/9a1e5598-9881-418d-ba18-f4281f52ecbb.jpg?1646143231",
                        "png", "https://cards.scryfall.io/png/front/9/a/9a1e5598-9881-418d-ba18-f4281f52ecbb.png?1646143231",
                        "art_crop", "https://cards.scryfall.io/art_crop/front/9/a/9a1e5598-9881-418d-ba18-f4281f52ecbb.jpg?1646143231",
                        "border_crop", "https://cards.scryfall.io/border_crop/front/9/a/9a1e5598-9881-418d-ba18-f4281f52ecbb.jpg?1646143231"
                ))
                .scryfallUri("https://scryfall.com/card/vow/149/pt/chandra-vestida-para-matar?utm_source=api")
                .typeLine("Legendary Planeswalker — Chandra")
                .printedTypeLine("Legendary Planeswalker — Chandra")
                .oracleText("+1: Add {R}. Chandra, Dressed to Kill deals 1 damage to up to one target player or planeswalker.\n+1: Exile the top card of your library. If it's red, you may cast it this turn.\n−7: Exile the top five cards of your library. You may cast red spells from among them this turn. You get an emblem with \"Whenever you cast a red spell, this emblem deals X damage to any target, where X is the amount of mana spent to cast that spell.\"")
                .printedText("+1: Add {R}. Chandra, Dressed to Kill deals 1 damage to up to one target player or planeswalker.\n+1: Exile the top card of your library. If it's red, you may cast it this turn.\n−7: Exile the top five cards of your library. You may cast red spells from among them this turn. You get an emblem with \"Whenever you cast a red spell, this emblem deals X damage to any target, where X is the amount of mana spent to cast that spell.\"")
                .rarity("mythic")
                .relatedUris(Map.of(
                        "gatherer", "https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=541004",
                        "tcgplayer_infinite_articles", "https://infinite.tcgplayer.com/search?contentMode=article&game=magic&partner=scryfall&q=Chandra%2C+Dressed+to+Kill&utm_campaign=affiliate&utm_medium=api&utm_source=scryfall",
                        "tcgplayer_infinite_decks", "https://infinite.tcgplayer.com/search?contentMode=deck&game=magic&partner=scryfall&q=Chandra%2C+Dressed+to+Kill&utm_campaign=affiliate&utm_medium=api&utm_source=scryfall",
                        "edhrec", "https://edhrec.com/route/?cc=Chandra%2C+Dressed+to+Kill"
                ))
                .colors(List.of("R"))
                .colorIdentity(Collections.singletonList("R"))
                .build();

        final CardEntity expectedCardEntity = CardEntity
                .builder()
                .id("681f7c73-92c6-47ba-af56-3ff032ac12da")
                .set(expectedSetEntity)
                .collectorNumber(149)
                .cardName("Chandra, Dressed to Kill")
                .printedName("Chandra, Dressed to Kill")
                .cardText("+1: Add {R}. Chandra, Dressed to Kill deals 1 damage to up to one target player or planeswalker.\n+1: Exile the top card of your library. If it's red, you may cast it this turn.\n−7: Exile the top five cards of your library. You may cast red spells from among them this turn. You get an emblem with \"Whenever you cast a red spell, this emblem deals X damage to any target, where X is the amount of mana spent to cast that spell.\"")
                .printedText("+1: Add {R}. Chandra, Dressed to Kill deals 1 damage to up to one target player or planeswalker.\n+1: Exile the top card of your library. If it's red, you may cast it this turn.\n−7: Exile the top five cards of your library. You may cast red spells from among them this turn. You get an emblem with \"Whenever you cast a red spell, this emblem deals X damage to any target, where X is the amount of mana spent to cast that spell.\"")
                .scryfallUri("https://scryfall.com/card/vow/149/pt/chandra-vestida-para-matar?utm_source=api")
                .imageUris(expectedImageUris)
                .manaCost("{1}{R}{R}")
                .cmc(3)
                .rarity(RarityEnum.MYTHIC)
                .gathererUri("https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=541004")
                .types(expectedTypeEntityList)
                .subtypes(expectedSubtypeEntityList)
                .colors("R")
                .colorIdentity("R")
                .build();

        when(setService.getSetByCode(eq(setCode))).thenReturn(expectedSetEntity);
        when(typeService.getTypesFromCardDTOTypeLine(eq(cardDTO.getTypeLine()))).thenReturn(expectedTypeEntityList);
        when(subtypeService.getSubtypesFromCardDTOTypeLine(eq(cardDTO.getTypeLine()))).thenReturn(expectedSubtypeEntityList);
        when(objectMapper.writeValueAsString(eq(cardDTO.getImageUris()))).thenReturn(expectedImageUris);

        final CardEntity actualCardEntity = cardEntityBuilder.build(cardDTO, setCode);

        Assertions.assertEquals(expectedCardEntity, actualCardEntity);
        verify(setService, times(1)).getSetByCode(eq(setCode));
        verify(typeService, times(1)).getTypesFromCardDTOTypeLine(eq(cardDTO.getTypeLine()));
        verify(subtypeService, times(1)).getSubtypesFromCardDTOTypeLine(eq(cardDTO.getTypeLine()));
        verify(objectMapper, times(1)).writeValueAsString(eq(cardDTO.getImageUris()));
    }
}