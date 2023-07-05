package com.mtgcollectionorganizer.api.user.repository;

import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPasswordRepository extends CrudRepository<UserPasswordEntity, String> {

    @Query(
        value = "SELECT u FROM UserPasswordEntity u " +
                "WHERE u.user.id = :userId " +
                "AND u.active = true "
    )
    Optional<UserPasswordEntity> findByUserIdAndIsActive(@Param("userId") final String userId);

    @Query(
            value = "SELECT u FROM UserPasswordEntity u " +
                    "WHERE u.user.userName = :userName " +
                    "AND u.encryptedPassword = :password " +
                    "AND u.active = true "
    )
    Optional<UserPasswordEntity> findByUserNameAndPasswordAndIsActive(
            @Param("userName") final String username,
            @Param("password") final String password);

    @Query(
            value = "SELECT u FROM UserPasswordEntity u " +
                    "WHERE u.user.userName = :userName " +
                    "AND u.active = true "
    )
    Optional<UserPasswordEntity> findByUserNameAndIsActive(@Param("userName") final String userName);
}
