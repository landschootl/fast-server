package com.main.fastserver.Repositories;

import com.main.fastserver.Entity.Skill;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "skills", path = "skills")
public interface SkillRepository extends Neo4jRepository<Skill, Long> {
}
