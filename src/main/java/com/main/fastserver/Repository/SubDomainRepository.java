package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Sub_domain;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "subdomains", path = "subdomains")
public interface SubDomainRepository extends Neo4jRepository<Sub_domain, Long> {

}
