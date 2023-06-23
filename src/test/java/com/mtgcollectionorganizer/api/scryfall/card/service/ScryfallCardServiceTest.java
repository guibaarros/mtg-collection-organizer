package com.mtgcollectionorganizer.api.scryfall.card.service;

import com.mtgcollectionorganizer.api.scryfall.card.client.ScryfallCardClient;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ScryfallCardServiceTest {

    @Mock
    private ScryfallCardClient cardClient;

    @InjectMocks
    private ScryfallCardService cardService;

    @Test
    @DisplayName("Test ScryfallCardService.getCardBySetCodeAndCollectorNumber")
    void testGetCardBySetCodeAndCollectorNumber(){
        final String setCode = "vow";
        final Integer collectorNumber = 149;
        final String language = "pt";

        final ScryfallCardDTO expectedScryfallCardDTO = ScryfallCardDTO
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

        when(cardClient.getCardBySetCodeAndCollectorNumber(
                eq(setCode),
                eq(collectorNumber),
                eq(language)
        )).thenReturn(expectedScryfallCardDTO);

        final ScryfallCardDTO actualCardBySetCodeAndCollectorNumber = cardService.getCardBySetCodeAndCollectorNumber(setCode, collectorNumber, language);

        Assertions.assertEquals(expectedScryfallCardDTO, actualCardBySetCodeAndCollectorNumber);
    }

    //TODO adicionar cenario de card nao encontrado no scryfall
}
