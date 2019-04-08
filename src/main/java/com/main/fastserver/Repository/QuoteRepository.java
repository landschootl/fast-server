package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Quote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Repository for quote
 */
@RepositoryRestResource(collectionResourceRel = "quotes", path = "quotes")
public interface QuoteRepository extends Neo4jRepository<Quote, Long> {
    Optional<Quote> findById(Long id);
}
