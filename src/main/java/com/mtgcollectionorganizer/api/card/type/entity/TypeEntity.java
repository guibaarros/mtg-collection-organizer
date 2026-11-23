package com.mtgcollectionorganizer.api.card.type.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "types")
public class TypeEntity {
    @Id
    @Column(name="id", updatable = false)
    @EqualsAndHashCode.Exclude
    private String id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name = "printed_name")
    private String printedName;
}
