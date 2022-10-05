package com.mtgcollectionorganizer.api.user.collection.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "user_card_tags")
public class UserCardTagEntity {

    @Id
    @SequenceGenerator(name = "user_card_tags_id_seq", sequenceName = "user_card_tags_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_card_tags_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;// bigint [pk, increment]

    @Column(name = "name")
    private String name;// varchar

    @ManyToOne
    @JoinColumn(name = "user_card_id", foreignKey = @ForeignKey(name = "user_card_tags_user_card_id"))
    private UserCardEntity userCard;// varchar [ref:> user_cards.id]
}
