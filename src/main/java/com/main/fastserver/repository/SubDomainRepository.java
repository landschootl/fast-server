package com.main.fastserver.repository;

import com.main.fastserver.entity.SubDomain;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * repository for subdomain
 */
@RepositoryRestResource(collectionResourceRel = "subdomains", path = "subdomains")
public interface SubDomainRepository extends Neo4jRepository<SubDomain, Long> {

    /**
     * Define the query for get all subdomain in the database
     */
    @Query("MATCH (sub_domain:SubDomain)<-[skill_in:SKILL_IN]-(skill:Skill) RETURN sub_domain, skill_in, skill")
    List<SubDomain> collectAll();

}
