package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Domain;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "domains", path = "domains")
public interface DomainRepository extends Neo4jRepository<Domain, Long> {

    List<Domain> getAllByTitle(@RequestParam(required = false) String title);

    @Query("MATCH (domain:Domain)<-[sub_in:SUB_DOMAIN_IN]-(sub_domain:Sub_domain)<-[skill_in:SKILL_IN]-(skill:Skill) RETURN domain, sub_in, sub_domain, skill_in, skill")
    List<Domain> collectAll();

}
