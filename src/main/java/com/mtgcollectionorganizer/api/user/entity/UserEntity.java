package com.mtgcollectionorganizer.api.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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
