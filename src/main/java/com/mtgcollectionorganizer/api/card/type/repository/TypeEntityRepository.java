package com.mtgcollectionorganizer.api.card.type.repository;

import com.mtgcollectionorganizer.api.card.type.entity.TypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeEntityRepository extends CrudRepository<TypeEntity, String> {

    Optional<TypeEntity> findByName(final String name);
}
