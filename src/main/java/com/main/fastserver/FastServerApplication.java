package com.main.fastserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Run the server in port 8080
 */
@SpringBootApplication
@EnableNeo4jRepositories ("com.main.fastserver.Repository")
@EnableScheduling
public class FastServerApplication {

	/**
	 * run springapplication
	 */
	public static void main(String[] args) {
		SpringApplication.run(FastServerApplication.class, args);
	}

}
