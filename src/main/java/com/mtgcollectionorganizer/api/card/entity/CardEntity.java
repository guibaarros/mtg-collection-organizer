package com.mtgcollectionorganizer.api.card.entity;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
public class CardEntity {

    @Id
    @Column(name = "id")
    private String id; //varchar [pk]

    @ManyToOne(cascade = CascadeType.ALL)
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "card_types",
            joinColumns = @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_card_types_card_id")),
            inverseJoinColumns = @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "fk_card_types_type_id"))
    )
    private List<TypeEntity> types; //many to many

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "card_subtypes",
            joinColumns = @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_card_subtypes_card_id")),
            inverseJoinColumns = @JoinColumn(name = "subtype_id", foreignKey = @ForeignKey(name = "fk_card_subtypes_subtype_id"))
    )
    private List<SubtypeEntity> subtypes;//many to many
}
