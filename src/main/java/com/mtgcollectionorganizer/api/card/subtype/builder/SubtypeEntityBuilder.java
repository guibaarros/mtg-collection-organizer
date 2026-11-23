package com.mtgcollectionorganizer.api.card.subtype.builder;

import org.springframework.util.StringUtils;
import com.mtgcollectionorganizer.api.card.subtype.builder.exception.InvalidSubtypeNameException;
import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubtypeEntityBuilder {

    public SubtypeEntity build(final String name){
        if (!StringUtils.hasLength(name)){
            throw new InvalidSubtypeNameException("name can not be null or empty");
        }
        return SubtypeEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}
