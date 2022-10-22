package com.mtgcollectionorganizer.api.scryfall.card.client;

import com.mtgcollectionorganizer.api.scryfall.card.dto.ScryfallCardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cardClient", url = "https://api.scryfall.com/cards/")
public interface ScryfallCardClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{setCode}/{collectorNumber}/{language}", produces = "application/json")
    ScryfallCardDTO getCardBySetCodeAndCollectorNumber(
            final @PathVariable("setCode") String setCode,
            final @PathVariable("collectorNumber") Integer collectorNumber,
            final @PathVariable(value = "language", required = false) String language
            );
}
