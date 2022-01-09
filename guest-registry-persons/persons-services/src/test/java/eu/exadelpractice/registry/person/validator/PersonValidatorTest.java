package eu.exadelpractice.registry.person.validator;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonValidatorTest {

	@Mock
	private PersonRepository personRepository;
	@InjectMocks
	private PersonValidator personValidator;
	private Person mockPerson = new Person("dasf", "35186744635", "First", "Last", Gender.MALE, LocalDate.of(1996, 6, 24),
			new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123", "FL@example.com");

	@Test
	public void mandatoryFields() throws Exception {
		try {
			personValidator.mandatoryFields(mockPerson);
		} catch (PersonNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void mandatoryFieldsWhenFailed() throws Exception {
		mockPerson.setName(null);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personValidator.mandatoryFields(mockPerson));
		assertEquals(ex.getMessage(), "Required fields are not given");
	}

	@Test
	public void id() throws Exception {
		try {
			personValidator.id(mockPerson);
		} catch (PersonNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void idWhenIdNull() throws Exception {
		mockPerson.setId(null);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> personValidator.id(mockPerson));
		assertEquals(ex.getMessage(), "Person id value is not given");
	}

	@Test
	public void validateNewPersonUnsuccessfully() throws Exception {
		String id = "dafsdaf";
		mockPerson.setId(id);
		Person mockPerson2 = new Person("Fdfdsa", "35186744635", "First", "Last", Gender.MALE,
				LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish",
				"+135186123", "FL@example.com");
		Mockito.when(personRepository.findByNameSurnameDate(mockPerson.getName(), mockPerson.getSurname(),
				mockPerson.getDateOfBirth())).thenReturn(mockPerson);
		Exception ex = assertThrows(PersonNotFoundException.class,
				() -> personValidator.validateNewPerson(mockPerson2));
		assertEquals(ex.getMessage(), "This person is already created");
	}

	@Test
	public void validateNewPersonSuccessfully() throws Exception {
		String id = "dafsdaf";
		mockPerson.setId(id);
		Person mockPerson2 = new Person("Fdfdsa", "35186744635", "Niels", "Last", Gender.MALE,
				LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish",
				"+135186123", "FL@example.com");
		//Mockito.when(personRepository.findByNameSurnameDate(mockPerson.getName(), mockPerson.getSurname(),
				//mockPerson.getDateOfBirth())).thenReturn(mockPerson);
		try {
			personValidator.validateNewPerson(mockPerson2);
		} catch (PersonNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}
}
