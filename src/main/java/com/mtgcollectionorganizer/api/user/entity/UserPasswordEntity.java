package com.mtgcollectionorganizer.api.user.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_passwords")
public class UserPasswordEntity {

    @Id
    @SequenceGenerator(name = "user_password_id_seq", sequenceName = "user_password_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_password_id_seq")
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "encrypted_password")
    private String encryptedPassword;// varchar

    @Column(name = "created_at")
    private LocalDateTime createdAt;// datetime

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;// datetime

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_password_user_id"))
    private UserEntity user;// varchar [ref:> users.id]

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
