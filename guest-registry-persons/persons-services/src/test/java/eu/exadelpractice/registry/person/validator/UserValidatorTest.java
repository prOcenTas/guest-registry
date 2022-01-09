package eu.exadelpractice.registry.person.validator;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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

import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Role;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import eu.exadelpractice.registry.person.repository.UserRepository;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;

//@SpringBootTest
//@RunWith(SpringRunner.class)
//@ComponentScan(basePackages = "eu.exadelpractice.registry")
@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {
	@Mock
	private PersonRepository personRepository;
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserValidator userValidator;

	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");
	// private Person mockPerson2 = new Person("513dsaf", "35186767635", "Max",
	// "Smith", Gender.FEMALE,
	// LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"),
	// "american", "+135786123",
	// "ms@example.com");
	private User mockUser = new User("fdasbvdfbfs", mockPerson, "1351215", Role.ADMIN);

	// private User mockUser2 = new User("DSAFVXZcva", mockPerson2, "135145123115",
	// Role.USER);

	@Test
	public void id() throws Exception {
		try {
			userValidator.id(mockUser);
		} catch (UserNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void idWhenIdNull() throws Exception {
		mockUser.setId(null);
		Exception ex = assertThrows(UserNotFoundException.class, () -> userValidator.id(mockUser));
		assertEquals(ex.getMessage(), "User id value is not given");
	}

	@Test
	public void validateNewUserSuccessfully() throws Exception {
		Mockito.when(personRepository.findById(mockUser.getPerson().getId())).thenReturn(Optional.of(mockPerson));
		// Mockito.when(userRepository.findByPerson_id(mockUser.getPerson().getId())).thenReturn(null);
		try {
			userValidator.validateNewUser(mockUser);
		} catch (ObjectNotFoundException e) {
			fail("Should not have thrown exception");
		}
	}

	@Test
	public void validateNewUserWhenPersonNotGiven() throws Exception {
		mockUser.setPerson(null);
		Exception ex = assertThrows(PersonNotFoundException.class, () -> userValidator.validateNewUser(mockUser));
		assertEquals(ex.getMessage(), "Person not given");
	}

	@Test
	public void validateNewUserWhenPersonDoesNotExist() throws Exception {
		Exception ex = assertThrows(PersonNotFoundException.class, () -> userValidator.validateNewUser(mockUser));
		assertEquals(ex.getMessage(), "Person does not exist");
	}

	@Test
	public void validateNewUserWhenUserAlreadyExists() throws Exception {
		Mockito.when(personRepository.findById(mockUser.getPerson().getId())).thenReturn(Optional.of(mockPerson));
		User mockUsersame = new User("fdsafdasfdfd", mockPerson, "dfadfsda", Role.ADMIN);
		Mockito.when(userRepository.findByPerson_id(mockUser.getPerson().getId())).thenReturn(mockUsersame);
		Exception ex = assertThrows(UserNotFoundException.class, () -> userValidator.validateNewUser(mockUser));
		assertEquals(ex.getMessage(), "This user already exists");
	}
}
