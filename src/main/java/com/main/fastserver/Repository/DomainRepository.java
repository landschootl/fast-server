package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Domain;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Repository for domain
 */
@RepositoryRestResource(collectionResourceRel = "domains", path = "domains")
public interface DomainRepository extends Neo4jRepository<Domain, Long> {

    /**
     * define query for search domain with title equal to the parameter
     * @param title of the domain sought
     * @return
     */
    Domain findByTitle(@RequestParam(required = false) String title);

    /**
     * Define the query for get all domain with relation in the database
     */
    @Query("MATCH (domain:Domain)\n" +
            "OPTIONAL MATCH (domain)<-[subdomain_in:SUB_DOMAIN_IN]-(subdomain:SubDomain)\n" +
            "OPTIONAL MATCH (subdomain)<-[skill_in:SKILL_IN]-(skill:Skill) \n" +
            "RETURN domain, subdomain_in, subdomain, skill_in, skill")
    List<Domain> collectAll();

}
