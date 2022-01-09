package eu.exadelpractice.registry.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "eu.exadelpractice.registry.cards", "eu.exadelpractice.registry",
		"eu.exadelpractice.registry.cards.repository" })
public class MainTestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(MainTestClass.class, args);
	}

}
/*
 * @RunWith(SpringRunner.class)
 * 
 * @SpringBootTest public class MainTestClass {
 * 
 * @Test public void contextLoads() { } }
 */
