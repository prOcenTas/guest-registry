package eu.exadelpractice.registry.person;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = { "eu.exadelpractice.registry" })
//@EnableMongoRepositories(basePackages = { "eu.exadelpractice.registry.cards.repository",
//		"eu.exadelpractice.registry.person.repository", "eu.exadelpractice.registry.locations.repository",
//		"eu.exadelpractice.registry.events.repository" })
//@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}

}
