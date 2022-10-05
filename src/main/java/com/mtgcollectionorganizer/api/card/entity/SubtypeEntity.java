package com.mtgcollectionorganizer.api.card.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "subtypes",
    uniqueConstraints = @UniqueConstraint(name="unique_subtypes", columnNames = {"name", "printed_name"})
)
public class SubtypeEntity {

    @Id
    @Column(name="id", updatable = false)
    private String id;

    @Column(name="name")
    private String name;

    @Column(name = "printed_name")
    private String printedName;

    @ManyToMany
    @JoinTable(
            name = "card_subtypes",
            joinColumns = @JoinColumn(name = "subtype_id", foreignKey = @ForeignKey(name = "fk_card_subtypes_subtype_id")),
            inverseJoinColumns = @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_card_subtypes_card_id"))
    )
    private List<CardEntity> cards;
}
