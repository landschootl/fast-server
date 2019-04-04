package com.main.fastserver.Entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Entity skill present in the database
 */
@NodeEntity
@Data
public class Skill {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
}
