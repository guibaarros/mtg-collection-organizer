package com.mtgcollectionorganizer.api.user.collection.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mtgcollectionorganizer.api.user.collection.entity.StateEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserCardDTO {
    private String id;
    private Boolean isFoil;
    private Boolean isPromo;
    private StateEnum state;
    private String extras;
    private Integer quantity;
    private CardDTO card;
}
