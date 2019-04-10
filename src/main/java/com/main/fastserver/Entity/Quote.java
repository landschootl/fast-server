package com.main.fastserver.Entity;

import com.sun.org.apache.xpath.internal.operations.Quo;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Entity quote
 */
@NodeEntity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
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
}
