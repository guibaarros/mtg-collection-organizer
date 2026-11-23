package com.mtgcollectionorganizer.api.scryfall.card.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScryfallCardDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("campos snake_case e campos extras devem ser desserializados após o upgrade para Jackson 3")
    void shouldDeserializeSnakeCaseFieldsAndIgnoreUnknownProperties() throws Exception {
        final String json = """
                {
                  "object": "card",
                  "id": "681f7c73-92c6-47ba-af56-3ff032ac12da",
                  "set": "vow",
                  "collector_number": "149",
                  "name": "Chandra, Dressed to Kill",
                  "printed_name": null,
                  "mana_cost": "{1}{R}{R}",
                  "cmc": 3,
                  "image_uris": {"small": "https://cards.scryfall.io/small.jpg"},
                  "scryfall_uri": "https://scryfall.com/card/vow/149",
                  "type_line": "Legendary Planeswalker — Chandra",
                  "oracle_text": "Texto original",
                  "rarity": "mythic",
                  "related_uris": {"gatherer": "https://gatherer.wizards.com/541004"},
                  "colors": ["R"],
                  "color_identity": ["R"],
                  "campo_novo_da_api": "deve ser ignorado"
                }
                """;

        final ScryfallCardDTO dto = objectMapper.readValue(json, ScryfallCardDTO.class);

        assertEquals("149", dto.getCollectorNumber());
        assertEquals("Chandra, Dressed to Kill", dto.getName());
        assertEquals("Legendary Planeswalker — Chandra", dto.getTypeLine());
        assertEquals("https://gatherer.wizards.com/541004", dto.getRelatedUris().get("gatherer"));
        assertEquals("R", dto.getColors().get(0));
        assertNull(dto.getPrintedName());
        assertNull(dto.getPrintedText());
    }
}
