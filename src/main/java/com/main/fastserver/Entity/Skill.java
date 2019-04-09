package com.main.fastserver.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Entity skill present in the database
 */
@NodeEntity
@Getter
@Setter
public class Skill {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;

    public Skill() {}

    public Skill(String title, String description) {
        this.description = description;
        this.title = title;
    }
}
