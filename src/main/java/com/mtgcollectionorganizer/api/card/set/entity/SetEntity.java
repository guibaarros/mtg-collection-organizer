package com.mtgcollectionorganizer.api.card.set.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Entity
@Table(name = "sets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class SetEntity {

    @Id
    @Column(name="id", updatable = false)
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="localized_name")
    private String localizedName;

    @Column(name="code", unique = true)
    private String code;

    @Column(name="scryfall_uri")
    private String scryfallUri;

    @Column(name="icon_url")
    private String iconUri;

    @Column(name="card_count")
    private Integer cardCount;
}
