package com.mtgcollectionorganizer.api.user.collection.entity;

import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "user_collections")
public class UserCollectionEntity {

    @Id
    @SequenceGenerator(name="user_collections_id_seq", sequenceName = "user_collections_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_collections_id_seq")
    @Column(name = "id")
    private Integer id;// varchar [pk]

    @Column(name = "name")
    private String name;// varchar

    @Column(name = "description")
    private String description;// varchar

    @Column(name = "created_at")
    private LocalDateTime createdAt;// datetime

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;// datetime

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_collection_user_id"))
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "user_collection_cards",
            joinColumns = @JoinColumn(name = "user_collection_id", foreignKey = @ForeignKey(name = "fk_user_collection_cards_user_collection_id")),
            inverseJoinColumns = @JoinColumn(name = "user_card_id", foreignKey = @ForeignKey(name = "fk_user_collection_cards_user_card_id"))
    )
    private List<UserCardEntity> cards; // many to many

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}