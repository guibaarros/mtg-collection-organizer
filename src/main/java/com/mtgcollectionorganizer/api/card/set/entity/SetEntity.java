package com.mtgcollectionorganizer.api.card.set.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "sets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
