package com.mtgcollectionorganizer.api.user.entity;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @Column(name = "id")
    private String id;// varchar [pk]

    @Column(name = "description")
    private String description;// varchar\

    @Column(name = "created_at")
    private LocalDateTime createdAt;// datetime

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;// datetime

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_roles_role_id")),
            inverseJoinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_roles_user_id"))
    )
    private List<UserEntity> users;

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
