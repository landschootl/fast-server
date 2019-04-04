package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Quote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "quotes", path = "quotes")
public interface QuoteRepository extends Neo4jRepository<Quote, Long> {
}
