package com.mtgcollectionorganizer.api.card.type.builder;

import com.google.common.base.Strings;
import com.mtgcollectionorganizer.api.card.type.builder.exception.InvalidTypeNameException;
import com.mtgcollectionorganizer.api.card.type.entity.TypeEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TypeEntityBuilder {

    public TypeEntity build(final String name){
        if (Strings.isNullOrEmpty(name)){
            throw new InvalidTypeNameException("name can not be null or empty");
        }
        return TypeEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}
