package com.mtgcollectionorganizer.api.card.subtype.builder;

import com.google.common.base.Strings;
import com.mtgcollectionorganizer.api.card.subtype.builder.exception.InvalidSubtypeNameException;
import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubtypeEntityBuilder {

    public SubtypeEntity build(final String name){
        if (Strings.isNullOrEmpty(name)){
            throw new InvalidSubtypeNameException("name can not be null or empty");
        }
        return SubtypeEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}
