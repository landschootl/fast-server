package com.main.fastserver.Entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Entity sub_domain present in the datagraphe
 */
@NodeEntity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubDomain {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @Relationship(type = "SKILL_IN", direction = Relationship.INCOMING)
    public List<Skill> skills;
}
