package com.main.fastserver.repository;

import com.main.fastserver.entity.Quote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * repository for quote
 */
@RepositoryRestResource(collectionResourceRel = "quotes", path = "quotes")
public interface QuoteRepository extends Neo4jRepository<Quote, Long> {
    Optional<Quote> findById(Long id);
}
