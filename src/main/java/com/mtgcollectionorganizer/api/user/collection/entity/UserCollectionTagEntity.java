package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "user_collection_tags")
public class UserCollectionTagEntity {
    @Id
    @SequenceGenerator(name = "user_collection_tags_id_seq", sequenceName = "user_collection_tags_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_collection_tags_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;// int [pk, increment]

    @Column(name = "name")
    private String name;// varchar
}
