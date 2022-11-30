package com.mtgcollectionorganizer.api.scryfall.card.service;

import com.mtgcollectionorganizer.api.scryfall.card.client.ScryfallCardClient;
import com.mtgcollectionorganizer.api.scryfall.card.dto.ScryfallCardDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScryfallCardService {

    private final ScryfallCardClient cardClient;

    public ScryfallCardDTO getCardBySetCodeAndCollectorNumber(final String setCode, final Integer collectorNumber, final String language) {
        return cardClient.getCardBySetCodeAndCollectorNumber(setCode, collectorNumber, language);
    }
}
