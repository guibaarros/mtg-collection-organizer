package com.mtgcollectionorganizer.api.card.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
}
