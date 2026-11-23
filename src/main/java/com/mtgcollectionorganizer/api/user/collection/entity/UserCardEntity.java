package com.mtgcollectionorganizer.api.user.collection.entity;

import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "user_cards")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCardEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "is_foil")
    private Boolean isFoil;

    @Column(name = "is_promo")
    private Boolean isPromo;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @Column(name = "extras")
    private String extras;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_card_user_id"))
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_user_card_card_id"))
    private CardEntity card;

    @OneToMany
    @JoinColumn(name = "user_card_id", foreignKey = @ForeignKey(name = "user_card_tags_user_card_id"))
    private List<UserCardTagEntity> tags;

    @PrePersist
    private void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addQuantity(final Integer quantityToBeAdded){
        this.quantity = Integer.sum(this.quantity, quantityToBeAdded);
    }
}
