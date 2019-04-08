package com.main.fastserver.Entity;

import com.sun.org.apache.xpath.internal.operations.Quo;
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
 * Entity quote
 */
@NodeEntity
@Getter
@Setter
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

    public Quote() {}

    public Quote(String name, String mail, String tel, String description, List<Skill> skills) {
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        this.description = description;
        this.skills = skills;
    }
}
