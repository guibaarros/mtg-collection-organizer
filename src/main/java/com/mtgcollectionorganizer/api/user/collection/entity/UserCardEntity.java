package com.mtgcollectionorganizer.api.user.collection.entity;

import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "user_cards")
public class UserCardEntity {

    @Id
    @Column(name = "id")
    private Integer id;// varchar [pk]

    @Column(name = "is_foil")
    private Boolean isFoil;

    @Column(name = "is_promo")
    private Boolean isPromo;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateEnum state;// varchar

    @Column(name = "extras")
    private String extras;// varchar

    @Column(name = "quantity")
    private Integer quantity;//int

    @Column(name = "created_at")
    private LocalDateTime createdAt;// datetime

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;// datetime

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_card_user_id"))
    private UserEntity user;// varchar [ref:> users.id]

    @ManyToOne
    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_user_card_card_id"))
    private CardEntity card;// [ref:> cards.id]

    @OneToMany
    @JoinColumn(name = "user_card_id", foreignKey = @ForeignKey(name = "user_card_tags_user_card_id"))
    private List<UserCardTagEntity> tags; // one to many
}
