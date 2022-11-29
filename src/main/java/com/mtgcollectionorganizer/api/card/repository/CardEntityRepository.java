package com.mtgcollectionorganizer.api.card.repository;

import com.mtgcollectionorganizer.api.card.entity.CardEntity;
import com.mtgcollectionorganizer.api.user.collection.entity.LanguageEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardEntityRepository extends CrudRepository<CardEntity, String> {

    @Query("SELECT c FROM CardEntity c WHERE c.set.code = ?1 AND c.collectorNumber = ?2 and c.language = ?3")
    Optional<CardEntity> findBySetCodeAndCollectorNumber(final String setCode, final Integer collectorNumber, final LanguageEnum language);
}
