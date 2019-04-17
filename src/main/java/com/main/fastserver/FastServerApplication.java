package com.main.fastserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Run the server in port 8080
 */
@SpringBootApplication
@EnableNeo4jRepositories ("com.main.fastserver.Repository")
@EnableScheduling
@EnableSwagger2
public class FastServerApplication {

	/**
	 * run springapplication
	 */
	public static void main(String[] args) {
		SpringApplication.run(FastServerApplication.class, args);
	}

}