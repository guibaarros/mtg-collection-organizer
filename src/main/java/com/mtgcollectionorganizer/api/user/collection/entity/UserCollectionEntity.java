package com.mtgcollectionorganizer.api.user.collection.entity;

import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "user_collections")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectionEntity {

    @Id
    @Column(name = "id")
    private String id;// varchar [pk]

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

    @OneToMany
    @JoinColumn(name = "user_collection_id", foreignKey = @ForeignKey(name = "user_collection_tags_user_card_id"))
    private List<UserCollectionTagEntity> tags;

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
