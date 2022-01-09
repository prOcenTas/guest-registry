package eu.exadelpractice.registry.person.service;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.model.WorkerType;
import eu.exadelpractice.registry.person.repository.WorkerRepository;
import eu.exadelpractice.registry.person.service.impl.WorkerServiceImpl;
import eu.exadelpractice.registry.person.validator.WorkerValidator;

@ExtendWith(MockitoExtension.class)
public class WorkerServiceImplTest {
	@Mock
	private WorkerRepository workerRepository;

	@Mock
	private WorkerValidator workerValidator;

	@InjectMocks
	private WorkerServiceImpl workerServiceImpl;

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
	public void getWorkerByIdWhenWorkerExists() throws Exception {
		Optional<Worker> optionalWorker = Optional.of(mockWorker);
		Mockito.when(workerRepository.findById(mockWorker.getId())).thenReturn(optionalWorker);
		assertEquals(workerServiceImpl.getWorkerById(mockWorker.getId()), mockWorker);
	}

	@Test
	public void getWorkerByIdWhenWorkerIsNull() throws Exception {
		String id = "156sdaffcxv12";
		Optional<Worker> optionalWorker = Optional.ofNullable(null);
		Mockito.when(workerRepository.findById(id)).thenReturn(optionalWorker);
		Exception ex = assertThrows(WorkerNotFoundException.class, () -> workerServiceImpl.getWorkerById(id));
		assertTrue(ex.getMessage().equals("Worker does not exist with an id " + id));
	}

	@Test
	public void findAll() throws Exception {
		List<Worker> workerList = new ArrayList<Worker>();
		workerList.add(mockWorker);
		workerList.add(mockWorker2);
		Mockito.when(workerRepository.findAll()).thenReturn(workerList);
		assertEquals(workerServiceImpl.findAll(), workerList);
	}

	@Test
	public void createWorker() throws Exception {
		Mockito.doAnswer(new Answer<Void>() {
		    @Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
		      mockWorker.setId("fdfsa");
		      //System.out.println("called with arguments: " + Arrays.toString(args));
		      //return null;
			return null;
		    }
		}).when(workerRepository).save(mockWorker);
		//Mockito.when(workerRepository.save(mockWorker)).thenReturn(mockWorker);
		assertTrue(workerServiceImpl.createWorker(mockWorker).equals("fdfsa"));
	}

	@Test
	public void createWorkerWhenValidationFails() throws Exception {
		Mockito.doThrow(new WorkerNotFoundException("This worker already exists")).when(workerValidator)
				.validateNewWorker(mockWorker);
		//Mockito.when(workerRepository.save(mockWorker)).thenReturn(mockWorker);
		Exception ex = assertThrows(WorkerNotFoundException.class, () -> workerServiceImpl.createWorker(mockWorker));
		assertTrue(ex.getMessage().equals("This worker already exists"));
	}

	@Test
	public void updateWorkerWhenWorkerExists() throws Exception {
		String id = "151fdsafsdaf";
		mockWorker.setId(id);
		mockWorker2.setId(id);
		Optional<Worker> optionalWorker = Optional.of(mockWorker);
		Mockito.when(workerRepository.findById(id)).thenReturn(optionalWorker);
		//Mockito.when(workerRepository.save(mockWorker2)).thenReturn(mockWorker2);
		workerServiceImpl.updateWorker(mockWorker2);
		Mockito.verify(workerRepository, Mockito.times(1)).findById(id);
		Mockito.verify(workerRepository, Mockito.times(1)).save(mockWorker2);
	}

	@Test
	public void updateWorkerWhenWorkerDosNotExist() throws Exception {
		String id = "FDafdsaf";
		Optional<Worker> optionalWorker = Optional.ofNullable(null);
		Mockito.when(workerRepository.findById(id)).thenReturn(optionalWorker);
		mockWorker2.setId(id);
		Exception ex = assertThrows(WorkerNotFoundException.class, () -> workerServiceImpl.updateWorker(mockWorker2));
		assertTrue(ex.getMessage().equals("Worker does not exist with an id " + id));
		Mockito.verify(workerRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(workerRepository, Mockito.times(1)).findById(id);
	}

	@Test
	public void updateWorkerWhenIdNotGiven() throws Exception {
		Mockito.doThrow(new WorkerNotFoundException("Worker id value is not given")).when(workerValidator)
				.id(mockWorker2);
		Exception ex = assertThrows(WorkerNotFoundException.class, () -> workerServiceImpl.updateWorker(mockWorker2));
		assertTrue(ex.getMessage().equals("Worker id value is not given"));
		Mockito.verify(workerRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(workerRepository, Mockito.never()).findById(Mockito.any());
	}

	@Test
	public void deleteWorkerByIdWhenWorkerExists() throws Exception {
		String id = mockWorker.getId();
		Optional<Worker> optionalWorker = Optional.of(mockWorker);
		Mockito.when(workerRepository.findById(id)).thenReturn(optionalWorker);
		workerServiceImpl.deleteWorkerById(id);
		Mockito.verify(workerRepository, Mockito.times(1)).findById(id);
		Mockito.verify(workerRepository, Mockito.times(1)).deleteById(id);
	}

	@Test
	public void deleteWorkerByIdWhenWorkerDoesNotExist() throws Exception {
		String id = "DFsafsdaf";
		Optional<Worker> optionalWorker = Optional.ofNullable(null);
		Mockito.when(workerRepository.findById(id)).thenReturn(optionalWorker);
		Exception ex = assertThrows(WorkerNotFoundException.class, () -> workerServiceImpl.deleteWorkerById(id));
		assertTrue(ex.getMessage().equals("Worker does not exist with an id " + id));
		Mockito.verify(workerRepository, Mockito.times(1)).findById(id);
		Mockito.verify(workerRepository, Mockito.never()).deleteById(id);
	}
}
