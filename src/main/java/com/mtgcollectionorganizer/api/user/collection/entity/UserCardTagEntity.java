package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
