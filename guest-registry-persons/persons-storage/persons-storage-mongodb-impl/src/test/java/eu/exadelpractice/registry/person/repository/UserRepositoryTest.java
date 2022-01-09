package eu.exadelpractice.registry.person.repository;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringRunner;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Role;
import eu.exadelpractice.registry.person.model.User;

@DataMongoTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MainTestClass.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@EnableMongoRepositories(basePackages = { "eu.exadelpractice.registry.users.repository",
//		"eu.exadelpractice.registry.person.repository", "eu.exadelpractice.registry.locations.repository" })
@EnableAutoConfiguration
//@SpringBootConfiguration
@ComponentScan(basePackages = {"eu.exadelpractice.registry.person"})
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PersonRepository personRepository;

	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");
	private Person mockPerson2 = new Person("513dsaf", "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");
	private User mockUser = new User("fdasbvdfbfs", mockPerson, "1351215", Role.ADMIN);

	private User mockUser2 = new User("DSAFVXZcva", mockPerson2, "135145123115", Role.USER);

	@Test
	public void findByIdWhenUserExists() throws Exception {
		String id = new ObjectId().toString();
		mockUser.setId(id);
		personRepository.save(mockPerson);
		userRepository.save(mockUser);
		assertTrue(userRepository.findById(id) instanceof Optional<?>);
		Optional<User> optionalUser = userRepository.findById(id);
		assertTrue(optionalUser.isPresent());
		User w = optionalUser.orElseThrow();
		assertTrue(w.equals(mockUser));
	}

	@Test
	public void findByIdWhenUserDoesNotExist() throws Exception {
		String id = new ObjectId().toString();
		assertTrue(personRepository.findById(id) instanceof Optional<?>);
		Optional<User> optionalUser = userRepository.findById(id);
		assertTrue(optionalUser.isEmpty());
	}

	@Test
	public void findAll() throws Exception {
		personRepository.save(mockPerson);
		personRepository.save(mockPerson2);
		userRepository.save(mockUser);
		userRepository.save(mockUser2);
		assertTrue(userRepository.findAll() instanceof List<?>);
		List<User> userList = userRepository.findAll();
		assertEquals(userList.size(), 2);
		// System.out.println(personList);
		assertTrue(userList.get(0).equals(mockUser));
		assertEquals(userList.get(1), mockUser2);
	}

	@Test
	public void save() throws Exception { // same with findbyid
		String id = new ObjectId().toString();
		mockUser.setId(id);
		personRepository.save(mockPerson);
		userRepository.save(mockUser);
		assertTrue(userRepository.findById(id) instanceof Optional<?>);
		Optional<User> optionalUser = userRepository.findById(id);
		assertTrue(optionalUser.isPresent());
		User w = optionalUser.orElseThrow();
		assertTrue(w.equals(mockUser));
		assertEquals(userRepository.findAll().size(), 1);
	}

	@Test
	public void deleteById() throws Exception {
		String id = mockUser.getId();
		userRepository.save(mockUser);
		assertTrue(userRepository.findById(id) instanceof Optional<?>);
		Optional<User> optionalUser = userRepository.findById(id);
		assertTrue(optionalUser.isPresent());
		userRepository.deleteById(id);
		assertTrue(userRepository.findById(id) instanceof Optional<?>);
		optionalUser = userRepository.findById(id);
		assertTrue(optionalUser.isEmpty());
	}

	@Test
	public void findByPerson_id() throws Exception {
		personRepository.save(mockPerson);
		userRepository.save(mockUser);
		User w = userRepository.findByPerson_id(mockPerson.getId());
		assertEquals(w, mockUser);
	}

}
