package com.mtgcollectionorganizer.api.card.set.repository;

import com.mtgcollectionorganizer.api.card.set.entity.SetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetEntityRepository extends CrudRepository<SetEntity, String> {

    Optional<SetEntity> findByCode(final String code);
}
