package com.main.fastserver.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Entity domain present in the database
 */
@NodeEntity
@Getter
@Setter
public class Domain {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String icon;

    @Relationship(type = "SUB_DOMAIN_IN", direction = Relationship.INCOMING)
    public List<Sub_domain> subdomains;

    public Domain() {}

    public Domain(String title, String icon, List<Sub_domain> subdomains) {
        this.title = title;
        this.icon = icon;
        this.subdomains = subdomains;
    }
}
