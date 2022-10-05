package com.mtgcollectionorganizer.api.card.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
public class CardEntity {

    @Id
    @Column(name = "id")
    private String id; //varchar [pk]

    @ManyToOne
    @JoinColumn(name="set_id", nullable = false, foreignKey = @ForeignKey(name = "fk_card_set_id"))
    private SetEntity set; //varchar [ref:> sets.id]

    @Column(name = "collector_number")
    private Integer collectorNumber;// int

    @Column(name = "card_name")
    private String cardName;// varchar

    @Column(name = "printed_name")
    private String printedName;// varchar

    @Column(name = "card_text")
    private String cardText;// varchar

    @Column(name = "printed_text")
    private String printedText;// varchar

    @Column(name = "scryfall_uri")
    private String scryfallUri;// varchar

    @Column(name = "images_uri")
    private String imageUris;// varchar

    @Column(name = "mana_cost")
    private String manaCost;// varchar

    @Column(name = "cmc")
    private Integer cmc; //int

    @Column(name = "rarity")
    @Enumerated(EnumType.STRING)
    private RarityEnum rarity; //varchar

    @Column(name = "gatherer_uri")
    private String gathererUri; //varchar

    @Column(name = "scryfall_response")
    private String scryfallResponse; //varchar

    @ManyToMany(mappedBy = "cards")
    private List<TypeEntity> types; //many to many

    @ManyToMany(mappedBy = "cards")
    private List<SubtypeEntity> subtypes;//many to many
}
