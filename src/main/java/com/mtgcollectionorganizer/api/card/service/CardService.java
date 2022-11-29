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

    public CardEntity getBySetCodeAndCollectorNumber(final String setCode, final Integer collectorNumber, final LanguageEnum language) {
        final var optionalCardEntity = cardEntityRepository.findBySetCodeAndCollectorNumber(setCode, collectorNumber, language);
        return optionalCardEntity.orElseGet(() -> buildCardEntityByScryfallData(setCode, collectorNumber, language));
    }

    private CardEntity buildCardEntityByScryfallData(final String setCode, final Integer collectorNumber, final LanguageEnum language) {
        final ScryfallCardDTO cardDTO = scryfallCardService
                .getCardBySetCodeAndCollectorNumber(
                        setCode,
                        collectorNumber,
                        language.name().toLowerCase(Locale.ROOT)
                );

        final CardEntity cardEntity = cardEntityBuilder.build(cardDTO, setCode, language);
        cardEntityRepository.save(cardEntity);

        return cardEntity;
    }
}
