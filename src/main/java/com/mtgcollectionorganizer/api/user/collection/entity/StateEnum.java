package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StateEnum {
    MINT("M"),
    NEAR_MINT("NM"),
    SLIGHTLY_PLAYED("SP"),
    MODERATELY_PLAYED("MP"),
    HEAVILY_PLAYED("HP"),
    DAMAGED("D");

    @Getter
    private final String stateAbbreviation;
}
