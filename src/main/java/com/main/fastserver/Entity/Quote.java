package com.main.fastserver.Entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Data
public class Quote {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String mail;
    private String tel;
    private String description;

    @Relationship(type = "HAS_SKILL")
    public List<Skill> skills;
}
