package com.mtgcollectionorganizer.api.controller;

import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.card.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{setCode}/{collectorNumber}")
    public ResponseEntity<CardEntity> getCardBySetIdAndCollectorNumber(
            @PathVariable("setCode") final String setCode,
            @PathVariable("collectorNumber") final Integer collectorNumber){

        final CardEntity cardEntity = cardService.getBySetCodeAndCollectorNumber(setCode, collectorNumber);
        return ResponseEntity.ok(cardEntity);
    }
}
