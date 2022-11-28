package com.mtgcollectionorganizer.api.card.builder;

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
import java.util.Objects;

@Service
@AllArgsConstructor
public class CardEntityBuilder {

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
                .printedName(Objects.nonNull(cardDTO.getPrintedName()) ? cardDTO.getPrintedName() : cardDTO.getName())
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
                .colors(getReplacedColors(cardDTO.getColors().toString()))
                .colorIdentity(getReplacedColors(cardDTO.getColorIdentity().toString()))
                .build();
    }

    private String getImageUris(final ScryfallCardDTO cardDTO) {
        try {
            return objectMapper.writeValueAsString(cardDTO.getImageUris());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getReplacedColors(final String color){
        return color
                .replace(",", "")
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "");
    }
}
