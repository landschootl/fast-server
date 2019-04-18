package com.main.fastserver.Entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Entity quote present in database
 */
@NodeEntity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quote {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String mail;
    private String tel;
    private String description;
    private boolean send;

    @Relationship(type = "HAS_SKILL")
    public List<Skill> skills;
}
