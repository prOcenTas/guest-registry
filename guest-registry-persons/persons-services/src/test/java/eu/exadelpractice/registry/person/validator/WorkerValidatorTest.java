package eu.exadelpractice.registry.person.validator;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.model.WorkerType;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import eu.exadelpractice.registry.person.repository.WorkerRepository;

@ExtendWith(MockitoExtension.class)
public class WorkerValidatorTest {
	@Mock
	private PersonRepository personRepository;
	@Mock
	private WorkerRepository workerRepository;
	//@Mock
	//private LocationRepository locationRepository;
	@Mock
	private PersonValidator personValidator;
	/*@Mock
	private LocationValidator locationValidator;
	@Mock
	private CardRepository cardRepository;
	@Mock
	private CardValidator cardValidator;*/
	@InjectMocks
	private WorkerValidator workerValidator;
	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");

	private LocationRef location = new LocationRef("1", "Meeting with x company");
	private CardRef card = new CardRef("DSfdsafdfvnmmn", "worker card");
	private Worker mockWorker = new Worker("pokfjdfd", mockPerson, WorkerType.FULL_TIME, card, "Exadel", "Executive",
			"JR developer", location, LocalDate.of(2013, 6, 24), 500.0);

	@Test
	public void id() throws Exception {
		try {
			workerValidator.id(mockWorker);
		} catch (WorkerNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void idWhenIdNull() throws Exception {
		mockWorker.setId(null);
		Exception ex = assertThrows(WorkerNotFoundException.class, () -> workerValidator.id(mockWorker));
		assertEquals(ex.getMessage(), "Worker id value is not given");
	}

	@Test
	public void validateNewWorkerSuccessfully() throws Exception {
		Mockito.when(personRepository.findById(mockWorker.getPerson().getId())).thenReturn(Optional.of(mockPerson));
		//Mockito.when(locationRepository.findById(mockWorker.getLocationOfOffice().getLocationId()))
		//		.thenReturn(Optional.of(mockLocation));
		//Mockito.when(cardRepository.findById(mockWorker.getCard().getId())).thenReturn(Optional.of(card));
		// Mockito.when(workerRepository.findByPerson_id(mockWorker.getPerson().getId())).thenReturn(null);
		try {
			workerValidator.validateNewWorker(mockWorker);
		} catch (ObjectNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void validateNewWorkerWhenPersonNotGiven() throws Exception {
		mockWorker.setPerson(null);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> workerValidator.validateNewWorker(mockWorker));
		assertEquals(ex.getMessage(), "Person not given");
	}

	@Test
	public void validateNewWorkerWhenPersonDoesNotExist() throws Exception {
		Exception ex = assertThrows(PersonNotFoundException.class, () -> workerValidator.validateNewWorker(mockWorker));
		assertEquals(ex.getMessage(), "Person does not exist");
	}

	@Test
	public void validateNewWorkerWhenWorkerAlreadyExists() throws Exception {
		Mockito.when(personRepository.findById(mockWorker.getPerson().getId())).thenReturn(Optional.of(mockPerson));
		Worker mockWorkersame = new Worker("fdsafdasf", mockPerson, WorkerType.FULL_TIME, card, "Exadel", "Executive",
				"JR developer", location, LocalDate.of(2013, 6, 24), 500.0);
		Mockito.when(workerRepository.findByPerson_id(mockWorker.getPerson().getId())).thenReturn(mockWorkersame);
		Exception ex = assertThrows(WorkerNotFoundException.class, () -> workerValidator.validateNewWorker(mockWorker));
		assertEquals(ex.getMessage(), "This worker already exists");
	}

	/*@Test
	public void validateNewWorkerWhenLocationDoesNotExist() throws Exception {
		Mockito.when(personRepository.findById(mockWorker.getPerson().getId())).thenReturn(Optional.of(mockPerson));
		Exception ex = assertThrows(LocationNotFoundException.class,
				() -> workerValidator.validateNewWorker(mockWorker));
		assertEquals(ex.getMessage(), "Location does not exist");
	}*/
}
