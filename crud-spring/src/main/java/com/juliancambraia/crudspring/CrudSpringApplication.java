package com.juliancambraia.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.juliancambraia.crudspring.enums.Category;
import com.juliancambraia.crudspring.model.Course;
import com.juliancambraia.crudspring.model.Lesson;
import com.juliancambraia.crudspring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	@Profile("test")
	CommandLineRunner initDatabase(CourseRepository repository) {
		return args -> {
			repository.deleteAllInBatch();

			for (int i = 0; i < 20; i++) {
				Course c = new Course();
				c.setName("Angular com Spring " + i);
				c.setCategory(Category.FRONT_END);

				Lesson l = new Lesson();
				l.setName("Introdução");
				l.setYoutubeUrl("watch?v=11");
				l.setCourse(c);

				c.getLessons().add(l);

				Lesson l2 = new Lesson();
				l2.setName("Angular");
				l2.setYoutubeUrl("watch?v=22");
				l2.setCourse(c);

				c.getLessons().add(l2);

				repository.saveAndFlush(c);
			}
		};
	}
}
