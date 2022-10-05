package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.Getter;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "user_collection_id", foreignKey = @ForeignKey(name = "user_collection_tags_user_card_id"))
    private UserCollectionEntity userCollection;// varchar [ref:> user_collections.id]
}
