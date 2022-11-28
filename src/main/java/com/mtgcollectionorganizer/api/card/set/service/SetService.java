package com.mtgcollectionorganizer.api.card.set.service;

import com.mtgcollectionorganizer.api.card.set.builder.SetEntityBuilder;
import com.mtgcollectionorganizer.api.card.set.entity.SetEntity;
import com.mtgcollectionorganizer.api.card.set.repository.SetEntityRepository;
import com.mtgcollectionorganizer.api.scryfall.set.dto.ScryfallSetDTO;
import com.mtgcollectionorganizer.api.scryfall.set.service.ScryfallSetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SetService {

    private final SetEntityBuilder setEntityBuilder;

    private final SetEntityRepository setEntityRepository;

    private final ScryfallSetService scryfallSetService;

    public SetEntity buildSetEntityByScryfallData(final ScryfallSetDTO setDTO) {
        return setEntityBuilder.build(setDTO);
    }

    public SetEntity getSetByCode(final String code) {
        return setEntityRepository
                .findByCode(code)
                .orElseGet(() -> buildSetEntityByScryfallData(
                            scryfallSetService.getSetByCode(code)
                        )
                );
    }
}
