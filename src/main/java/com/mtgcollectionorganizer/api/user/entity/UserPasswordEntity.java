package com.mtgcollectionorganizer.api.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_passwords")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordEntity {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "encrypted_password")
    private String encryptedPassword;// varchar

    @Column(name = "created_at")
    private LocalDateTime createdAt;// datetime

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;// datetime

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_password_user_id"))
    private UserEntity user;// varchar [ref:> users.id]

    @Column(name = "active")
    private Boolean active;

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void disable(){
        this.active = Boolean.FALSE;
    }
}
