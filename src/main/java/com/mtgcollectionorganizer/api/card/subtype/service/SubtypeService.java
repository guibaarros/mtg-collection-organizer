package com.mtgcollectionorganizer.api.card.subtype.service;

import com.google.common.base.Strings;
import com.mtgcollectionorganizer.api.card.subtype.builder.SubtypeEntityBuilder;
import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import com.mtgcollectionorganizer.api.card.subtype.repository.SubtypeEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SubtypeService {

    private final SubtypeEntityBuilder subtypeEntityBuilder;
    private final SubtypeEntityRepository subtypeEntityRepository;

    public List<SubtypeEntity> getSubtypesFromCardDTOTypeLine(final String typeLine) {
        final List<SubtypeEntity> subtypeList = new ArrayList<>();

        if (!Strings.isNullOrEmpty(typeLine)){
            final String[] split = typeLine.split("—");

            if (split.length > 1){
                final String[] types = split[1].trim().split(" ");

                for (String type : types) {
                    subtypeList.add(getSubtypeEntityByName(type));
                }
            }
        }

        return subtypeList;
    }

    private SubtypeEntity getSubtypeEntityByName(final String name) {
        return subtypeEntityRepository.findByName(name)
                .orElseGet(() -> subtypeEntityBuilder.build(name));
    }
}
