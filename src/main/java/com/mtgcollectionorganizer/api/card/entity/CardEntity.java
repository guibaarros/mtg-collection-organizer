package com.mtgcollectionorganizer.api.card.entity;

import com.mtgcollectionorganizer.api.card.set.entity.SetEntity;
import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import com.mtgcollectionorganizer.api.card.type.entity.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
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

    @Column(name = "card_text", length = 4096)
    private String cardText;// varchar

    @Column(name = "printed_text", length = 4096)
    private String printedText;// varchar

    @Column(name = "scryfall_uri", length = 1024)
    private String scryfallUri;// varchar

    @Column(name = "images_uri", length = 1024)
    private String imageUris;// varchar

    @Column(name = "mana_cost")
    private String manaCost;// varchar

    @Column(name = "cmc")
    private Integer cmc; //int

    @Column(name = "rarity")
    @Enumerated(EnumType.STRING)
    private RarityEnum rarity; //varchar

    @Column(name = "gatherer_uri", length = 1024)
    private String gathererUri; //varchar

    @Column(name = "colors", length = 1024)
    private String colors;

    @Column(name = "color_identity", length = 1024)
    private String colorIdentity;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
