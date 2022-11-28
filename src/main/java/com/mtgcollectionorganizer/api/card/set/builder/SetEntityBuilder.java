package com.mtgcollectionorganizer.api.card.set.builder;

import com.mtgcollectionorganizer.api.card.set.entity.SetEntity;
import com.mtgcollectionorganizer.api.scryfall.set.dto.ScryfallSetDTO;
import org.springframework.stereotype.Service;

@Service
public class SetEntityBuilder {
    public SetEntity build(final ScryfallSetDTO setDTO) {
        return SetEntity.builder()
                .id(setDTO.getId())
                .name(setDTO.getName())
                .localizedName(setDTO.getName())
                .code(setDTO.getCode())
                .scryfallUri(setDTO.getScryfallUri())
                .iconUri(setDTO.getIconSvgUri())
                .cardCount(setDTO.getCardCount())
                .build();
    }
}
