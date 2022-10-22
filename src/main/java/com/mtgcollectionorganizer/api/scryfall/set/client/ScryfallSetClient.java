package com.mtgcollectionorganizer.api.scryfall.set.client;

import com.mtgcollectionorganizer.api.scryfall.set.dto.ScryfallSetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "setClient", url = "https://api.scryfall.com/sets/")
public interface ScryfallSetClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{setCode}", produces = "application/json")
    ScryfallSetDTO getSetByCode(final @PathVariable("setCode") String setCode);
}
