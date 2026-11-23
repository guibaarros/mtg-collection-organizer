package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Entity
@Table(name = "user_card_tags")
public class UserCardTagEntity {

    @Id
    @Column(name = "id", updatable = false)
    private String id;// bigint [pk, increment]

    @Column(name = "name")
    private String name;// varchar

}
