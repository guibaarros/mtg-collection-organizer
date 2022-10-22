package com.mtgcollectionorganizer.api.scryfall.set.service;

import com.mtgcollectionorganizer.api.scryfall.set.client.ScryfallSetClient;
import com.mtgcollectionorganizer.api.scryfall.set.dto.ScryfallSetDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScryfallSetService {

    private final ScryfallSetClient setClient;

    public ScryfallSetDTO getSetByCode(final String setCode){
        return setClient.getSetByCode(setCode);
    }
}
