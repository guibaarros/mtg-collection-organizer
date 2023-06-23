package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "user_collection_tags")
public class UserCollectionTagEntity {
    @Id
    @Column(name = "id", updatable = false)
    private String id;// int [pk, increment]

    @Column(name = "name")
    private String name;// varchar
}
