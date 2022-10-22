package com.mtgcollectionorganizer.api.card.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.card.entity.RarityEnum;
import com.mtgcollectionorganizer.api.card.set.service.SetService;
import com.mtgcollectionorganizer.api.card.subtype.service.SubtypeService;
import com.mtgcollectionorganizer.api.card.type.service.TypeService;
import com.mtgcollectionorganizer.api.scryfall.card.dto.ScryfallCardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class CardBuilder {

    private final SetService setService;
    private final ObjectMapper objectMapper;
    private final TypeService typeService;
    private final SubtypeService subtypeService;

    public CardEntity build(final ScryfallCardDTO cardDTO, final String setCode) {
        return CardEntity.builder()
                .id(cardDTO.getId())
                .set(setService.getSetByCode(setCode))
                .collectorNumber(Integer.parseInt(cardDTO.getCollectorNumber()))
                .cardName(cardDTO.getName())
                .printedName(cardDTO.getPrintedName())
                .cardText(cardDTO.getOracleText())
                .printedText(cardDTO.getPrintedText())
                .scryfallUri(cardDTO.getScryfallUri())
                .imageUris(getImageUris(cardDTO))
                .manaCost(cardDTO.getManaCost())
                .cmc(cardDTO.getCmc())
                .rarity(RarityEnum.valueOf(cardDTO.getRarity().toUpperCase(Locale.ROOT)))
                .gathererUri(cardDTO.getRelatedUris().get("gatherer"))
                .types(typeService.getTypesFromCardDTOTypeLine(cardDTO.getTypeLine()))
                .subtypes(subtypeService.getSubtypesFromCardDTOTypeLine(cardDTO.getTypeLine()))
                .build();
    }

    private String getImageUris(ScryfallCardDTO cardDTO) {
        try {
            return objectMapper.writeValueAsString(cardDTO.getImageUris());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
