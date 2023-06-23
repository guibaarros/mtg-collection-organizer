package com.mtgcollectionorganizer.api.user.repository;

import com.mtgcollectionorganizer.api.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    Optional<UserEntity> findByUserName(String userName);
}
