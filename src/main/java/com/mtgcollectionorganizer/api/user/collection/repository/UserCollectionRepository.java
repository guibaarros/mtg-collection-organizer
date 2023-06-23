package com.mtgcollectionorganizer.api.user.collection.repository;

import com.mtgcollectionorganizer.api.user.collection.entity.UserCollectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionRepository extends CrudRepository<UserCollectionEntity, String> {
}
