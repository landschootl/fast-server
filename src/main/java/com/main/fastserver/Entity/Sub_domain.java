package com.main.fastserver.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Entity sub_domain present in the datagraphe
 */
@NodeEntity
@Getter
public class Sub_domain {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @Relationship(type = "SKILL_IN", direction = Relationship.INCOMING)
    public List<Skill> skills;

    public Sub_domain() {}

    public Sub_domain(String title, List<Skill> skills) {
        this.title = title;
        this.skills = skills;
    }

}
