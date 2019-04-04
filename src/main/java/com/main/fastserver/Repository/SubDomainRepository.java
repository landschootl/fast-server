package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Sub_domain;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository for subdomain
 */
@RepositoryRestResource(collectionResourceRel = "subdomains", path = "subdomains")
public interface SubDomainRepository extends Neo4jRepository<Sub_domain, Long> {

    /**
     * Define the query for get all subdomain in the database
     */
    @Query("MATCH (sub_domain:Sub_domain)<-[skill_in:SKILL_IN]-(skill:Skill) RETURN sub_domain, skill_in, skill")
    List<Sub_domain> collectAll();

}
