package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Sub_domain;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "subdomains", path = "subdomains")
public interface SubDomainRepository extends Neo4jRepository<Sub_domain, Long> {

    @Query("MATCH (sub_domain:Sub_domain)<-[skill_in:SKILL_IN]-(skill:Skill) RETURN sub_domain, skill_in, skill")
    public List<Sub_domain> collectAll();

}
