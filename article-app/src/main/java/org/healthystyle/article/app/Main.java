package org.healthystyle.article.app;

import org.healthystyle.article.model.Category;
import org.healthystyle.article.model.mark.Mark;
import org.healthystyle.article.repository.CategoryRepository;
import org.healthystyle.article.repository.mark.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.healthystyle.article")
@EnableJpaRepositories(basePackages = "org.healthystyle.article.repository")
@EntityScan(basePackages = "org.healthystyle.article.model")
public class Main {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private MarkRepository markRepository;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner r() {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				Category sportCategory = new Category("Спорт", 1);
				Category foodCategory = new Category("Еда", 2);
				Category healthCategory = new Category("Здоровье", 3);

//				categoryRepository.save(sportCategory);
//				categoryRepository.save(foodCategory);
//				categoryRepository.save(healthCategory);

//				markRepository.save(new Mark(1));
//				markRepository.save(new Mark(2));
//				markRepository.save(new Mark(3));
//				markRepository.save(new Mark(4));
//				markRepository.save(new Mark(5));
			}
		};
	}
}
