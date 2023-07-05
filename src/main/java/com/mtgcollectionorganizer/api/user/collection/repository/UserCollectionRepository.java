package com.mtgcollectionorganizer.api.user.collection.repository;

import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCollectionRepository extends CrudRepository<UserCollectionEntity, String> {
    Optional<UserCollectionEntity> findByIdAndUserId(final String id, final String userId);
}
