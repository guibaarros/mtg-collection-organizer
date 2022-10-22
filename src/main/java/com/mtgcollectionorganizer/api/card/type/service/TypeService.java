package com.mtgcollectionorganizer.api.card.type.service;

import com.google.common.base.Strings;
import com.mtgcollectionorganizer.api.card.type.builder.TypeEntityBuilder;
import com.mtgcollectionorganizer.api.card.type.entity.TypeEntity;
import com.mtgcollectionorganizer.api.card.type.repository.TypeEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {

    private final TypeEntityBuilder typeEntityBuilder;
    private final TypeEntityRepository typeEntityRepository;

    public List<TypeEntity> getTypesFromCardDTOTypeLine(final String typeLine) {
        final List<TypeEntity> typeList = new ArrayList<>();

        if (!Strings.isNullOrEmpty(typeLine)){
            final String[] split = typeLine.split("—");

            if (split.length > 0){
                final String[] types = split[0].split(" ");

                for (final String type : types) {
                    typeList.add(getTypeEntityByName(type));
                }
            }
        }

        return typeList;
    }

    private TypeEntity getTypeEntityByName(final String name){
        return typeEntityRepository.findByName(name)
                .orElseGet(() -> typeEntityBuilder.build(name));
    }
}
