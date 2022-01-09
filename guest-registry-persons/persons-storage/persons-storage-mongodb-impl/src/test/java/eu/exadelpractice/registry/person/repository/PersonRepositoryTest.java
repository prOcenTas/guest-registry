package eu.exadelpractice.registry.person.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Person;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

@DataMongoTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MainTestClass.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@EnableAutoConfiguration
//@SpringBootConfiguration
@ComponentScan(basePackages = {"eu.exadelpractice.registry.person"})
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository personRepository;

	private Person mockPerson = new Person(null, "35186744635", "First", "Last", Gender.MALE, LocalDate.of(1996, 6, 24),
			new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123", "FL@example.com");

	private Person mockPerson2 = new Person(null, "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");

	@Test
	public void findByIdWhenPersonExists() throws Exception {
		String id = new ObjectId().toString();
		mockPerson.setId(id);
		personRepository.save(mockPerson);
		assertTrue(personRepository.findById(id) instanceof Optional<?>);
		Optional<Person> optionalPerson = personRepository.findById(id);
		assertTrue(optionalPerson.isPresent());
		Person p = optionalPerson.orElseThrow();
		assertTrue(p.equals(mockPerson));
	}

	@Test
	public void findByIdWhenPersonDoesNotExist() throws Exception {
		String id = new ObjectId().toString();
		;
		assertTrue(personRepository.findById(id) instanceof Optional<?>);
		Optional<Person> optionalPerson = personRepository.findById(id);
		assertTrue(optionalPerson.isEmpty());
	}

	@Test
	public void findAll() throws Exception {
		String id = new ObjectId().toString();
		mockPerson.setId(id);
		personRepository.save(mockPerson);
		String id2 = new ObjectId().toString();
		mockPerson2.setId(id2);
		personRepository.save(mockPerson2);
		Map<String, String[]> map = new HashMap<String, String[]>();
		assertTrue(personRepository.findAll(map) instanceof List<?>);
		List<Person> personList = personRepository.findAll(map);
		assertEquals(personList.size(), 2);
		// System.out.println(personList);
		assertTrue(personList.get(0).equals(mockPerson));
		assertEquals(personList.get(1), mockPerson2);
	}

	@Test
	public void save() throws Exception { // same with findbyid
		//String id = new ObjectId().toString();
		//mockPerson.setId(id);
		personRepository.save(mockPerson);
		//assertTrue(ps.equals(mockPerson));
		assertTrue(personRepository.findById(mockPerson.getId()) instanceof Optional<?>);
		Optional<Person> optionalPerson = personRepository.findById(mockPerson.getId());
		assertTrue(optionalPerson.isPresent());
		Person p = optionalPerson.orElseThrow();
		assertTrue(p.equals(mockPerson));
		Map<String, String[]> map = new HashMap<String, String[]>();
		assertEquals(personRepository.findAll(map).size(), 1);
		// personRepository.findByNameSurnameDate(id, id, null)
	}

	@Test
	public void deleteById() throws Exception {
		String id = new ObjectId().toString();
		mockPerson.setId(id);
		personRepository.save(mockPerson);
		assertTrue(personRepository.findById(id) instanceof Optional<?>);
		Optional<Person> optionalPerson = personRepository.findById(id);
		assertTrue(optionalPerson.isPresent());
		personRepository.deleteById(id);
		assertTrue(personRepository.findById(id) instanceof Optional<?>);
		optionalPerson = personRepository.findById(id);
		assertTrue(optionalPerson.isEmpty());
	}

	@Test
	public void findByNameSurnameDate() throws Exception {
		String name = "First";
		String surname = "Last";
		LocalDate dateOfBirth = LocalDate.of(1996, 6, 24);
		personRepository.save(mockPerson);
		personRepository.save(mockPerson2);
		Person p = personRepository.findByNameSurnameDate(name, surname, dateOfBirth);
		assertTrue(p.equals(mockPerson));
	}
}
