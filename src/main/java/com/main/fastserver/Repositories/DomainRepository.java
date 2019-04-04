package com.main.fastserver.Repositories;

import com.main.fastserver.Entity.Domain;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "domains", path = "domains")
public interface DomainRepository extends Neo4jRepository<Domain, Long> {

    Domain findByTitle(@Param("title") String title);

    @Query("MATCH (d:Domain)<-[sr:SUB_DOMAIN_IN]-(s:Sub_domain)<-[skr:SKILL_IN]-(sk:Skill) RETURN d, sr, s, skr, sk")
    List<Domain> collectAll();

}
