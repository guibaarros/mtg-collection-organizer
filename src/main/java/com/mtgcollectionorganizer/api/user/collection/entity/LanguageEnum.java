package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LanguageEnum {
    EN("English"),
    PT_BR("Português");

    @Getter
    private final String name;
}
