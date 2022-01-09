package eu.exadelpractice.registry.person.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.OperationResult;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import eu.exadelpractice.registry.person.service.impl.PersonServiceImpl;
import eu.exadelpractice.registry.person.validator.PersonValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private PersonValidator personValidator;

	@InjectMocks
	private PersonServiceImpl personServiceImpl;

	private Person mockPerson = new Person("Fasdfsa", "35186744635", "First", "Last", Gender.MALE, LocalDate.of(1996, 6, 24),
			new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123", "FL@example.com");

	private Person mockPerson2 = new Person("Xasdfas", "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");

	@Test
	public void getPersonByIdWhenPersonExists() throws Exception {
		Optional<Person> optionalPerson = Optional.of(mockPerson);
		Mockito.when(personRepository.findById(mockPerson.getId())).thenReturn(optionalPerson);
		assertEquals(personServiceImpl.getPersonById(mockPerson.getId()), mockPerson);
	}

	@Test
	public void getPersonByIdWhenPersonIsNull() throws Exception {
		// mockPerson.setId(id);
		Optional<Person> optionalPerson = Optional.ofNullable(null);
		Mockito.when(personRepository.findById(mockPerson.getId())).thenReturn(optionalPerson);
		// assertEquals(personServiceImpl.getPersonById(id), mockPerson);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personServiceImpl.getPersonById(mockPerson.getId()));
		assertTrue(ex.getMessage().equals("Person not found with an id " + mockPerson.getId()));
	}

	@Test
	public void findAll() throws Exception {
		List<Person> personList = new ArrayList<Person>();
		personList.add(mockPerson);
		personList.add(mockPerson2);
		// Optional<List> optionalList = Optional.of(personList);
		Mockito.when(personRepository.findAll(null)).thenReturn(personList);
		assertEquals(personServiceImpl.findAll(null), personList);
	}

	@Test
	public void createPerson() throws Exception {
		Mockito.doAnswer(new Answer<Void>() {
		    @Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
		      mockPerson.setId("fdfsa");
		      //System.out.println("called with arguments: " + Arrays.toString(args));
		      //return null;
			return null;
		    }
		}).when(personRepository).save(mockPerson);
		//Mockito.when(personRepository.save(mockPerson)).thenReturn(mockPerson);
		// System.out.println(personServiceImpl.createPerson(mockPerson));
		assertTrue(personServiceImpl.createPerson(mockPerson).equals("fdfsa"));
	}

	@Test
	public void createPersonWhenMandatoryFielsNotGiven() throws Exception {
		Mockito.doThrow(new PersonNotFoundException("Required fields are not given")).when(personValidator)
				.validateNewPerson(mockPerson);
		//Mockito.when(personRepository.save(mockPerson)).thenReturn(mockPerson);
		// System.out.println(personServiceImpl.createPerson(mockPerson));
		// assertTrue(!personServiceImpl.createPerson(mockPerson).equals(null));
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personServiceImpl.createPerson(mockPerson));
		assertTrue(ex.getMessage().equals("Required fields are not given"));
	}

	@Test
	public void updatePersonWhenPersonExists() throws Exception {
		String id = "DSafsda";
		mockPerson.setId(id);
		mockPerson2.setId(id);
		Optional<Person> optionalPerson = Optional.of(mockPerson);
		Mockito.when(personRepository.findById(id)).thenReturn(optionalPerson);
		//Mockito.when(personRepository.save(mockPerson2)).thenReturn(mockPerson2);
		// assertEquals(personServiceImpl.updatePerson(mockPerson2), mockPerson2);
		// information.sendInfoForPublishing(person);
		// verify(publishing,times(1)).publishInformation(person);
		personServiceImpl.updatePerson(mockPerson2);
		// assertEquals(mockPerson, mockPerson2);
		Mockito.verify(personRepository, Mockito.times(1)).findById(id);
		Mockito.verify(personRepository, Mockito.times(1)).save(mockPerson2);
	}

	@Test
	public void updatePersonWhenPersonDosNotExist() throws Exception {
		String id = "lpklkf,mx";
		Optional<Person> optionalPerson = Optional.ofNullable(null);
		Mockito.when(personRepository.findById(id)).thenReturn(optionalPerson);
		mockPerson2.setId(id);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personServiceImpl.updatePerson(mockPerson2));
		assertTrue(ex.getMessage().equals("Person not found with an id " + id));
		Mockito.verify(personRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(personRepository, Mockito.times(1)).findById(id);
	}

	@Test
	public void updatePersonWhenIdNotGiven() throws Exception {
		Mockito.doThrow(new PersonNotFoundException("Person id value is not given")).when(personValidator)
				.id(mockPerson2);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personServiceImpl.updatePerson(mockPerson2));
		assertTrue(ex.getMessage().equals("Person id value is not given"));
		Mockito.verify(personRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(personRepository, Mockito.never()).findById(Mockito.any());
	}

	@Test
	public void deletePersonByIdWhenPersonExists() throws Exception {
		String id = "xaxaSd";
		mockPerson.setId(id);
		Optional<Person> optionalPerson = Optional.of(mockPerson);
		Mockito.when(personRepository.findById(id)).thenReturn(optionalPerson);
		personServiceImpl.deletePersonById(id);
		Mockito.verify(personRepository, Mockito.times(1)).findById(id);
		Mockito.verify(personRepository, Mockito.times(1)).deleteById(id);
	}

	@Test
	public void deletePersonByIdWhenPersonDoesNotExist() throws Exception {
		String id = "oisdakjf";
		Optional<Person> optionalPerson = Optional.ofNullable(null);
		Mockito.when(personRepository.findById(id)).thenReturn(optionalPerson);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personServiceImpl.deletePersonById(id));
		assertTrue(ex.getMessage().equals("Person not found with an id " + id));
		Mockito.verify(personRepository, Mockito.times(1)).findById(id);
		Mockito.verify(personRepository, Mockito.never()).deleteById(id);
	}

	@Test
	public void findOrCreateWhenPersonExists() throws Exception {
		Person mockP = new Person(null, null, "First", "Last", null, LocalDate.of(1996, 6, 24), null, null, null, null);
		String id = "poiuiytre";
		mockPerson.setId(id);
		// Optional<Person> optionalPerson = Optional.of(mockPerson);
		Mockito.when(personRepository.findByNameSurnameDate(mockPerson.getName(), mockPerson.getSurname(),
				mockPerson.getDateOfBirth())).thenReturn(mockPerson);
		assertEquals(personServiceImpl.findOrCreate(mockP), new OperationResult(false, mockPerson.getId()));
		Mockito.verify(personRepository, Mockito.times(1)).findByNameSurnameDate(mockPerson.getName(),
				mockPerson.getSurname(), mockPerson.getDateOfBirth());
		Mockito.verify(personRepository, Mockito.never()).save(Mockito.any());

	}

	@Test
	public void findOrCreateWhenPersonDosNotExist() throws Exception {
		Person mockP = new Person(null, null, "First", "Last", null, LocalDate.of(1996, 6, 24), null, null, null, null);
		// String id = new ObjectId().toString();
		Mockito.when(
				personRepository.findByNameSurnameDate(mockP.getName(), mockP.getSurname(), mockP.getDateOfBirth()))
				.thenReturn(null);
		// Mockito.when(personRepository)
		//Mockito.when(personRepository.save(mockP)).thenReturn(mockP);
		assertEquals(personServiceImpl.findOrCreate(mockP), new OperationResult(true, mockP.getId()));
		Mockito.verify(personRepository, Mockito.times(1)).findByNameSurnameDate(mockPerson.getName(),
				mockPerson.getSurname(), mockPerson.getDateOfBirth());
		Mockito.verify(personRepository, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void findOrCreateWhenValidationFails() throws Exception {
		Person mockP = new Person(null, null, null, "Last", null, LocalDate.of(1996, 6, 24), null, null, null, null);
		Mockito.doThrow(new PersonNotFoundException("Required fields are not given")).when(personValidator)
				.mandatoryFields(mockP);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personServiceImpl.findOrCreate(mockP));
		assertTrue(ex.getMessage().equals("Required fields are not given"));
		Mockito.verify(personRepository, Mockito.never()).findByNameSurnameDate(Mockito.any(), Mockito.any(),
				Mockito.any());
		Mockito.verify(personRepository, Mockito.never()).save(Mockito.any());
	}
}
