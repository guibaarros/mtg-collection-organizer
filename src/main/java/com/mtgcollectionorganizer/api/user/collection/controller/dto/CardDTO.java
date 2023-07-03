package com.mtgcollectionorganizer.api.user.collection.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mtgcollectionorganizer.api.user.collection.entity.LanguageEnum;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class CardDTO {
    private String setCode;
    private Integer collectorNumber;
    private LanguageEnum language;
}
