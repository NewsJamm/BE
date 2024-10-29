package com.NewsJam.NewsJam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableJpaAuditing
@EnableScheduling
public class NewsJamApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsJamApplication.class, args);
	}

}
