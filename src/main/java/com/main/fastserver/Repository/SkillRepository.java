package com.main.fastserver.Repository;

import com.main.fastserver.Entity.Skill;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for skill
 */
@RepositoryRestResource(collectionResourceRel = "skills", path = "skills")
public interface SkillRepository extends Neo4jRepository<Skill, Long> {

    @Query("MATCH (subdomain:SubDomain {title:{subDomain})<-[r:SKILL_IN]-(skill:Skill {title:{skill}}) DELETE r")
    void delete(@Param("subDomain") String SubDomain, @Param("skill") String skill);

}
