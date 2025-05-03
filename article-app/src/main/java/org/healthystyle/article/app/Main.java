package org.healthystyle.article.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.healthystyle.article")
@EnableJpaRepositories(basePackages = "org.healthystyle.article.repository")
@EntityScan(basePackages = "org.healthystyle.article.model")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
