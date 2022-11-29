package com.mtgcollectionorganizer.api.card.service;

import com.mtgcollectionorganizer.api.card.builder.CardEntityBuilder;
import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.card.repository.CardEntityRepository;
import com.mtgcollectionorganizer.api.scryfall.card.dto.ScryfallCardDTO;
import com.mtgcollectionorganizer.api.scryfall.card.service.ScryfallCardService;
import com.mtgcollectionorganizer.api.user.collection.entity.LanguageEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class CardService {

    private final CardEntityRepository cardEntityRepository;
    private final ScryfallCardService scryfallCardService;
    private final CardEntityBuilder cardEntityBuilder;

    //TODO add language parameter
    public CardEntity getBySetCodeAndCollectorNumber(final String setCode, final Integer collectorNumber) {
        final var optionalCardEntity = cardEntityRepository.findBySetCodeAndCollectorNumber(setCode, collectorNumber);
        return optionalCardEntity.orElseGet(() -> buildCardEntityByScryfallData(setCode, collectorNumber));
    }

    private CardEntity buildCardEntityByScryfallData(final String setCode, final Integer collectorNumber) {
        final ScryfallCardDTO cardDTO = scryfallCardService
                .getCardBySetCodeAndCollectorNumber(
                        setCode,
                        collectorNumber,
                        LanguageEnum.PT.name().toLowerCase(Locale.ROOT)
                );

        final CardEntity cardEntity = cardEntityBuilder.build(cardDTO, setCode);
        cardEntityRepository.save(cardEntity);

        return cardEntity;
    }
}
