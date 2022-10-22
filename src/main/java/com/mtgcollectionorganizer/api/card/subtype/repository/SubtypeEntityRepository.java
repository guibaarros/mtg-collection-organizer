package com.mtgcollectionorganizer.api.card.subtype.repository;

import com.mtgcollectionorganizer.api.card.subtype.entity.SubtypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubtypeEntityRepository extends CrudRepository<SubtypeEntity, String> {

    Optional<SubtypeEntity> findByName(final String name);
}
