package com.davidson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Run the server
 */
@SpringBootApplication
@EnableNeo4jRepositories ("com.davidson")
@EnableScheduling
public class Application {

	/**
	 * run springapplication
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}