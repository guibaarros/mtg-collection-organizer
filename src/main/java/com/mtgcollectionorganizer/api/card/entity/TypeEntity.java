package com.mtgcollectionorganizer.api.card.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "types",
        uniqueConstraints = @UniqueConstraint(name="unique_types", columnNames = {"name", "printed_name"})
)
public class TypeEntity {
    @Id
    @Column(name="id", updatable = false)
    private String id;

    @Column(name="name")
    private String name;

    @Column(name = "printed_name")
    private String printedName;

    @ManyToMany
    @JoinTable(
            name = "card_types",
            joinColumns = @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "fk_card_types_type_id")),
            inverseJoinColumns = @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_card_types_card_id"))
    )
    private List<CardEntity> cards;
}
