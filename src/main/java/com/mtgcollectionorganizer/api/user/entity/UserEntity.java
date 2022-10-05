package com.mtgcollectionorganizer.api.user.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;// varchar [pk]

    @Column(name = "display_name")
    private String displayName;// varchar

    @Column(name = "user_name")
    private String userName;// varchar

    @Column(name = "created_at")
    private LocalDateTime createdAt;// datetime

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;// datetime

    @Column(name = "email")
    private String email;// varchar

    @ManyToMany(mappedBy = "users")
    private List<RoleEntity> roles; //many to many

    @OneToMany(mappedBy = "user")
    private List<UserPasswordEntity> passwords;

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
