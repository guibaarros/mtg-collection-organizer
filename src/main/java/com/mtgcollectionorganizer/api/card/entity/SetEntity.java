package com.mtgcollectionorganizer.api.card.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "sets")
public class SetEntity {

    @Id
    @SequenceGenerator(name = "sets_id_seq", sequenceName = "sets_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sets_id_seq")
    @Column(name="id", updatable = false)
    private String id;

    @Column(name="name")
    private String name; //varchar

    @Column(name="localized_name")
    private String localizedName; //varchar

    @Column(name="code", unique = true)
    private String code;

    @Column(name="scryfall_uri")
    private String scryfallUri;

    @Column(name="icon_url")
    private String iconUri;

    @Column(name="card_count")
    private Integer cardCount;

    @Column(name="scryfall_response")
    private String scryfallResponse;

    @OneToMany(mappedBy = "set")
    private List<CardEntity> cards;
}
