package eu.exadelpractice.registry.person.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import eu.exadelpractice.registry.common.model.Address;
//import eu.exadelpractice.registry.person.MainTestClass;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.model.GuestType;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;

@DataMongoTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MainTestClass.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@EnableMongoRepositories(basePackages = { "eu.exadelpractice.registry.cards.repository",
//		"eu.exadelpractice.registry.person.repository", "eu.exadelpractice.registry.locations.repository" })
@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan(basePackages = {"eu.exadelpractice.registry.person"})
public class GuestRepositoryTest {
	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private PersonRepository personRepository;

	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");

	private Person mockPerson2 = new Person("513dsaf", "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");

	private LocationRef location = new LocationRef("1", "Meeting with x company");
	private CardRef card = new CardRef("DSfdsafdfvnmmn", "worker card");

	private Guest mockGuest = new Guest("dsafdsfvcxc", mockPerson, card, "Exadel", "Good", location, "noreason",
			GuestType.GUEST);
	private Guest mockGuest2 = new Guest("xzcvxcv", mockPerson2, card, "Google", "Wed", location, "good reason",
			GuestType.VISITOR);

	@Test
	public void findByIdWhenGuestExists() throws Exception {
		String id = new ObjectId().toString();
		mockGuest.setId(id);
		personRepository.save(mockPerson);
		//locationRepository.save(mockLocation);
		//cardRepository.save(card);
		guestRepository.save(mockGuest);
		assertTrue(guestRepository.findById(id) instanceof Optional<?>);
		Optional<Guest> optionalGuest = guestRepository.findById(id);
		assertTrue(optionalGuest.isPresent());
		Guest g = optionalGuest.orElseThrow();
		assertTrue(g.equals(mockGuest));
	}

	@Test
	public void findByIdWhenGuestDoesNotExist() throws Exception {
		String id = new ObjectId().toString();
		assertTrue(guestRepository.findById(id) instanceof Optional<?>);
		Optional<Guest> optionalGuest = guestRepository.findById(id);
		assertTrue(optionalGuest.isEmpty());
	}

	@Test
	public void findAll() throws Exception {
		personRepository.save(mockPerson);
		personRepository.save(mockPerson2);
		//locationRepository.save(mockLocation);
		//cardRepository.save(card);
		guestRepository.save(mockGuest);
		guestRepository.save(mockGuest2);
		assertTrue(guestRepository.findAll() instanceof List<?>);
		List<Guest> guestList = guestRepository.findAll();
		assertEquals(guestList.size(), 2);
		// System.out.println(personList);
		assertTrue(guestList.get(0).equals(mockGuest));
		assertEquals(guestList.get(1), mockGuest2);
	}

	@Test
	public void save() throws Exception { // same with findbyid
		String id = new ObjectId().toString();
		mockGuest.setId(id);
		personRepository.save(mockPerson);
		//locationRepository.save(mockLocation);
		//cardRepository.save(card);
		guestRepository.save(mockGuest);
		assertTrue(guestRepository.findById(id) instanceof Optional<?>);
		Optional<Guest> optionalGuest = guestRepository.findById(id);
		assertTrue(optionalGuest.isPresent());
		Guest g = optionalGuest.orElseThrow();
		assertTrue(g.equals(mockGuest));
		assertEquals(guestRepository.findAll().size(), 1);
	}

	@Test
	public void deleteById() throws Exception {
		String id = mockGuest.getId();
		guestRepository.save(mockGuest);
		assertTrue(guestRepository.findById(id) instanceof Optional<?>);
		Optional<Guest> optionalGuest = guestRepository.findById(id);
		assertTrue(optionalGuest.isPresent());
		guestRepository.deleteById(id);
		assertTrue(guestRepository.findById(id) instanceof Optional<?>);
		optionalGuest = guestRepository.findById(id);
		assertTrue(optionalGuest.isEmpty());
	}

}
