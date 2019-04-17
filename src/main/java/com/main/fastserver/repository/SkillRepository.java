package com.main.fastserver.repository;

import com.main.fastserver.entity.Skill;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * repository for skill
 */
@RepositoryRestResource(collectionResourceRel = "skills", path = "skills")
public interface SkillRepository extends Neo4jRepository<Skill, Long> {

    @Query("MATCH (skill) WHERE ID(skill) = {skillid} MATCH (subDomain:SubDomain)<-[r:SKILL_IN]-(skill) DELETE r")
    void delete(@Param("skillid") Long skillId);

}
