package com.mtgcollectionorganizer.api.user.repository;

import com.mtgcollectionorganizer.api.user.collection.entity.StateEnum;
import com.mtgcollectionorganizer.api.user.collection.entity.UserCardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCardRepository extends CrudRepository<UserCardEntity, String> {

    @Query("select uc from UserCardEntity uc " +
            "where " +
            "uc.card.id = :cardId and " +
            "uc.user.id = :userId and " +
            "uc.isFoil = :isFoil and " +
            "uc.isPromo = :isPromo and " +
            "uc.state = :state ")
    Optional<UserCardEntity> findByUniqueKey(
            @Param("cardId") final String cardId,
            @Param("userId") final String userId,
            @Param("isFoil") final Boolean isFoil,
            @Param("isPromo") final Boolean isPromo,
            @Param("state") final StateEnum state
    );
}
