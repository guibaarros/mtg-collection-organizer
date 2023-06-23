package com.mtgcollectionorganizer.api.card.controller;

import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.card.service.CardService;
import com.mtgcollectionorganizer.api.user.collection.entity.LanguageEnum;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{setCode}/{collectorNumber}/{language}")
    public ResponseEntity<CardEntity> getCardBySetIdAndCollectorNumber(
            @PathVariable("setCode") final String setCode,
            @PathVariable("collectorNumber") final Integer collectorNumber,
            @PathVariable(value = "language", required = false) final String language){

        final LanguageEnum languageEnum = Strings.isNotBlank(language) ? LanguageEnum.valueOf(language.toUpperCase(Locale.ROOT)) : LanguageEnum.PT;

        final CardEntity cardEntity = cardService.getBySetCodeAndCollectorNumber(setCode, collectorNumber, languageEnum);
        return ResponseEntity.ok(cardEntity);
    }
}
