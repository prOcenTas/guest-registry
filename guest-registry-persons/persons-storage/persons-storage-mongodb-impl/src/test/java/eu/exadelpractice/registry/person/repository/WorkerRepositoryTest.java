package eu.exadelpractice.registry.person.repository;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.model.WorkerType;

@DataMongoTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MainTestClass.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@EnableMongoRepositories(basePackages = { "eu.exadelpractice.registry.cards.repository",
//		"eu.exadelpractice.registry.person.repository", "eu.exadelpractice.registry.locations.repository" })
@EnableAutoConfiguration
//@SpringBootConfiguration
@ComponentScan(basePackages = {"eu.exadelpractice.registry.person"})
public class WorkerRepositoryTest {
	@Autowired
	private WorkerRepository workerRepository;
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
	private Worker mockWorker = new Worker("pokfjdfd", mockPerson, WorkerType.FULL_TIME, card, "Exadel", "Executive",
			"JR developer", location, LocalDate.of(2013, 6, 24), 500.0);

	private Worker mockWorker2 = new Worker("fd15adf1adhb", mockPerson2, WorkerType.TEMPORARY, card, "Google",
			"Communications", "Designer", location, LocalDate.of(2015, 6, 24), 600.0);

	@Test
	public void findByIdWhenWorkerExists() throws Exception {
		String id = new ObjectId().toString();
		mockWorker.setId(id);
		personRepository.save(mockPerson);
		workerRepository.save(mockWorker);
		assertTrue(workerRepository.findById(id) instanceof Optional<?>);
		Optional<Worker> optionalWorker = workerRepository.findById(id);
		assertTrue(optionalWorker.isPresent());
		Worker w = optionalWorker.orElseThrow();
		assertTrue(w.equals(mockWorker));
	}

	@Test
	public void findByIdWhenWorkerDoesNotExist() throws Exception {
		String id = new ObjectId().toString();
		assertTrue(personRepository.findById(id) instanceof Optional<?>);
		Optional<Worker> optionalWorker = workerRepository.findById(id);
		assertTrue(optionalWorker.isEmpty());
	}

	@Test
	public void findAll() throws Exception {
		personRepository.save(mockPerson);
		personRepository.save(mockPerson2);
		workerRepository.save(mockWorker);
		workerRepository.save(mockWorker2);
		assertTrue(workerRepository.findAll() instanceof List<?>);
		List<Worker> workerList = workerRepository.findAll();
		assertEquals(workerList.size(), 2);
		// System.out.println(personList);
		assertTrue(workerList.get(0).equals(mockWorker));
		assertEquals(workerList.get(1), mockWorker2);
	}

	@Test
	public void save() throws Exception { // same with findbyid
		//String id = new ObjectId().toString();
		//mockWorker.setId(id);
		personRepository.save(mockPerson);
		workerRepository.save(mockWorker);
		assertTrue(workerRepository.findById(mockWorker.getId()) instanceof Optional<?>);
		Optional<Worker> optionalWorker = workerRepository.findById(mockWorker.getId());
		assertTrue(optionalWorker.isPresent());
		Worker w = optionalWorker.orElseThrow();
		assertTrue(w.equals(mockWorker));
		assertEquals(workerRepository.findAll().size(), 1);
	}

	@Test
	public void deleteById() throws Exception {
		workerRepository.save(mockWorker);
		String id = mockWorker.getId();
		assertTrue(workerRepository.findById(id) instanceof Optional<?>);
		Optional<Worker> optionalWorker = workerRepository.findById(id);
		assertTrue(optionalWorker.isPresent());
		workerRepository.deleteById(id);
		assertTrue(workerRepository.findById(id) instanceof Optional<?>);
		optionalWorker = workerRepository.findById(id);
		assertTrue(optionalWorker.isEmpty());
	}

	@Test
	public void findByPerson_id() throws Exception {
		personRepository.save(mockPerson);
		workerRepository.save(mockWorker);
		Worker w = workerRepository.findByPerson_id(mockPerson.getId());
		assertEquals(w, mockWorker);
	}

}
