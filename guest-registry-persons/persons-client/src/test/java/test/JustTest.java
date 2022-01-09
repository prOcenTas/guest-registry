package test;

import java.time.LocalDate;
import java.util.List;

import eu.exadelpractice.registry.common.model.Address;

//import static org.junit.jupiter.api.Assertions.*;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import eu.exadelpractice.registry.person.client.GuestClient;
import eu.exadelpractice.registry.person.client.PersonClient;
import eu.exadelpractice.registry.person.client.UserClient;
import eu.exadelpractice.registry.person.client.WorkerClient;
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.model.GuestType;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Role;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.model.WorkerType;
import lombok.extern.log4j.Log4j2;

import org.springframework.test.context.TestPropertySource;
//import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest
//@EnableAutoConfiguration
//@SpringBootConfiguration
@ComponentScan(basePackages = { "eu.exadelpractice.registry.person.client", "eu.exadelpractice.registry.common.client" })
@SpringBootApplication
//@ConfigurationProperties("app")
@TestPropertySource(locations = "classpath:testing.properties")
@Log4j2
class JustTest {

	// private RestTemplate restTemplate;
	@Autowired
	private PersonClient personClient;
	@Autowired
	private WorkerClient workerClient;
	@Autowired
	private GuestClient guestClient;
	@Autowired
	private UserClient userClient;
	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");


	private LocationRef location = new LocationRef("1", "Meeting with x company");
	private CardRef card = new CardRef("DSfdsafdfvnmmn", "worker card");
	private Worker mockWorker = new Worker("pokfjdfd", mockPerson, WorkerType.FULL_TIME, card, "Exadel", "Executive",
			"JR developer", location, LocalDate.of(2013, 6, 24), 500.0);
	private Guest mockGuest = new Guest("dsafdsfvcxc", mockPerson, card, "Exadel", "Good", location, "noreason",
			GuestType.GUEST);
	private User mockUser = new User("fdasbvdfbfs", mockPerson, "1351215", Role.ADMIN);
	@Test
	void test() {
		/*
		 * restTemplate = new RestTemplate(); //fail("Not yet implemented"); String id =
		 * "618451b0e944bf685e5c4409"; String plainCreds = "user1:pass1"; byte[]
		 * plainCredsBytes = plainCreds.getBytes(); byte[] base64CredsBytes =
		 * Base64.encodeBase64(plainCredsBytes); String base64Creds = new
		 * String(base64CredsBytes);
		 * 
		 * HttpHeaders headers = new HttpHeaders(); headers.add("Authorization",
		 * "Basic " + base64Creds);
		 * 
		 * HttpEntity<String> request = new HttpEntity<String>(headers);
		 * ResponseEntity<Person> response = restTemplate.exchange(
		 * "http://localhost:8082/persons/618451b0e944bf685e5c4409", HttpMethod.GET,
		 * request, Person.class); //ResponseEntity<String> response = new
		 * RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
		 * 
		 * // get JSON response Person person = response.getBody();
		 * System.out.println(person.getName());
		 */
		try {
			Person p = personClient.getPersonById("618451b0e944bf65e5c4409");
			log.info("{}", p);
		} catch (PersonNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		try {
			List<Person> p = personClient.getAllPersons();
			log.info("{}", p);
		} catch (PersonNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		Person person = mockPerson;
		person.setName("Pablo");
		person.setSurname("Correz");
		person.setDateOfBirth(LocalDate.of(1997, 06, 24));
		try {
			String id = personClient.createPerson(person);
			log.info("{}", id);
		} catch (PersonNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		person.setId("619568784966ab58a1f7977b");
		person.setNationality("Italian");
		try {
			personClient.updatePerson(person);
			log.info("updated");
		} catch (PersonNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		try {
			personClient.deletePersonById("61953b7e4a3d62134a4e8018");
			log.info("deleted");
		} catch (PersonNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
			
		person.setName("Anthony");
		try {
			ResponseEntity<String> response = personClient.findOrCreate(person);
			if (response.getStatusCode() == HttpStatus.CREATED) {
				log.info("Person created : {}", response.getBody());
			} else if (response.getStatusCode() == HttpStatus.OK) {
				log.info("Person found : {}", response.getBody());
			}
		} catch (PersonNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
	}
	@Test
	void testWorker() {
		try {
			Worker w = workerClient.getWorkerById("61845204e944bf685e5c440b");
			log.info("{}", w);
		} catch (WorkerNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		try {
			List<Worker> w = workerClient.getAllWorkers();
			log.info("{}", w);
		} catch (WorkerNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		Person p = new Person();
		p.setId("618458c24f7aea44707f2600");
		mockWorker.setPerson(p);
		//worker.setName("Pablo");
		//person.setSurname("Correz");
		//person.setDateOfBirth(LocalDate.of(1996, 06, 24));
		try {
			String id = workerClient.createWorker(mockWorker);
			log.info("{}", id);
		} catch (WorkerNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		mockWorker.setId("618c3106a4c68372fd3e1ecd");
		mockWorker.setPosition("JR");
		try {
			workerClient.updateWorker(mockWorker);
			log.info("updated");
		} catch (WorkerNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}

		try {
			workerClient.deleteWorkerById("618c3106a4c68372fd3e1ecd");
			log.info("deleted");
		} catch (WorkerNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
	}
	
	@Test
	void testGuest() {
		try {
			Guest g = guestClient.getGuestById("618c3f28a4c68372fd3e1ece");
			log.info("{}", g);
		} catch (GuestNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		try {
			List<Guest> g = guestClient.getAllGuests();
			log.info("{}", g);
		} catch (GuestNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		Person p = new Person();
		p.setId("618458c24f7aea44707f2600");
		mockGuest.setPerson(p);
		//worker.setName("Pablo");
		//person.setSurname("Correz");
		//person.setDateOfBirth(LocalDate.of(1996, 06, 24));
		try {
			String id = guestClient.createGuest(mockGuest);
			log.info("{}", id);
		} catch (GuestNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		mockGuest.setId("618c3f28a4c68372fd3e1ece");
		mockGuest.setPosition("JR");
		try {
			guestClient.updateGuest(mockGuest);
			log.info("updated");
		} catch (GuestNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}

		try {
			guestClient.deleteGuestById("618c3f28a4c68372fd3e1ece");
			log.info("deleted");
		} catch (GuestNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
	}
	@Test
	void testUser() {
		try {
			User u = userClient.getUserById("618c40e9a4c68372fd3e1ed3");
			log.info("{}", u);
		} catch (UserNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		try {
			List<User> u = userClient.getAllUsers();
			log.info("{}", u);
		} catch (UserNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		Person p = new Person();
		p.setId("618458c24f7aea44707f2600");
		mockUser.setPerson(p);
		//worker.setName("Pablo");
		//person.setSurname("Correz");
		//person.setDateOfBirth(LocalDate.of(1996, 06, 24));
		try {
			String id = userClient.createUser(mockUser);
			log.info("{}", id);
		} catch (UserNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
		mockUser.setId("618c40e9a4c68372fd3e1ed3");
		mockUser.setPassword("aaaa");
		try {
			userClient.updateUser(mockUser);
			log.info("updated");
		} catch (UserNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}

		try {
			userClient.deleteUserById("618c40e9a4c68372fd3e1ed3");
			log.info("deleted");
		} catch (UserNotFoundException ex) {
			log.error("Error happened during rest call {}", ex.getMessage());
		} catch (BadRequestException ex) {
			log.error("BadRequest Error happened during rest call {}", ex.getMessage());
		} catch (InternalServerErrorException ex) {
			log.error("Internal Error happened during rest call {}", ex.getMessage());
		}
	}

}
