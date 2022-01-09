package eu.exadelpractice.registry.person.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Role;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.repository.UserRepository;
import eu.exadelpractice.registry.person.service.impl.UserServiceImpl;
import eu.exadelpractice.registry.person.validator.UserValidator;


//@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private UserValidator userValidator;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");
	private Person mockPerson2 = new Person("513dsaf", "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");
	private User mockUser = new User("fdasbvdfbfs", mockPerson, "1351215", Role.ADMIN);

	private User mockUser2 = new User("DSAFVXZcva", mockPerson2, "135145123115", Role.USER);

	@Test
	public void getUserByIdWhenUserExists() throws Exception {
		Optional<User> optionalUser = Optional.of(mockUser);
		Mockito.when(userRepository.findById(mockUser.getId())).thenReturn(optionalUser);
		assertEquals(userServiceImpl.getUserById(mockUser.getId()), mockUser);
	}

	@Test
	public void getUserByIdWhenUserIsNull() throws Exception {
		String id = "156sdaffcxv12";
		Optional<User> optionalUser = Optional.ofNullable(null);
		Mockito.when(userRepository.findById(id)).thenReturn(optionalUser);
		Exception ex = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(id));
		assertTrue(ex.getMessage().equals("User does not exist with an id " + id));
	}

	@Test
	public void findAll() throws Exception {
		List<User> userList = new ArrayList<User>();
		userList.add(mockUser);
		userList.add(mockUser2);
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		assertEquals(userServiceImpl.findAll(), userList);
	}

	@Test
	public void createUser() throws Exception {
		Mockito.doAnswer(new Answer<Void>() {
		    @Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
		      mockUser.setId("fdfsa");
		      //System.out.println("called with arguments: " + Arrays.toString(args));
		      //return null;
			return null;
		    }
		}).when(userRepository).save(mockUser);
		
		//Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
		assertTrue(userServiceImpl.createUser(mockUser).equals("fdfsa"));
	}

	@Test
	public void createUserWhenValidationFails() throws Exception {
		Mockito.doThrow(new UserNotFoundException("This user already exists")).when(userValidator)
				.validateNewUser(mockUser);
		//Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);
		Exception ex = assertThrows(UserNotFoundException.class, () -> userServiceImpl.createUser(mockUser));
		assertTrue(ex.getMessage().equals("This user already exists"));
	}

	@Test
	public void updateUserWhenUserExists() throws Exception {
		String id = "151fdsafsdaf";
		mockUser.setId(id);
		mockUser2.setId(id);
		Optional<User> optionalUser = Optional.of(mockUser);
		Mockito.when(userRepository.findById(id)).thenReturn(optionalUser);
		//Mockito.when(userRepository.save(mockUser2)).thenReturn(mockUser2);
		userServiceImpl.updateUser(mockUser2);
		Mockito.verify(userRepository, Mockito.times(1)).findById(id);
		Mockito.verify(userRepository, Mockito.times(1)).save(mockUser2);
	}

	@Test
	public void updateUserWhenUserDosNotExist() throws Exception {
		String id = "FDafdsaf";
		Optional<User> optionalUser = Optional.ofNullable(null);
		Mockito.when(userRepository.findById(id)).thenReturn(optionalUser);
		mockUser2.setId(id);
		Exception ex = assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(mockUser2));
		assertTrue(ex.getMessage().equals("User does not exist with an id " + id));
		Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(userRepository, Mockito.times(1)).findById(id);
	}

	@Test
	public void updateUserWhenIdNotGiven() throws Exception {
		Mockito.doThrow(new UserNotFoundException("User id value is not given")).when(userValidator).id(mockUser2);
		Exception ex = assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(mockUser2));
		assertTrue(ex.getMessage().equals("User id value is not given"));
		Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
		Mockito.verify(userRepository, Mockito.never()).findById(Mockito.any());
	}

	@Test
	public void deleteUserByIdWhenUserExists() throws Exception {
		String id = mockUser.getId();
		Optional<User> optionalUser = Optional.of(mockUser);
		Mockito.when(userRepository.findById(id)).thenReturn(optionalUser);
		userServiceImpl.deleteUserById(id);
		Mockito.verify(userRepository, Mockito.times(1)).findById(id);
		Mockito.verify(userRepository, Mockito.times(1)).deleteById(id);
	}

	@Test
	public void deleteUserByIdWhenUserDoesNotExist() throws Exception {
		String id = "DFsafsdaf";
		Optional<User> optionalUser = Optional.ofNullable(null);
		Mockito.when(userRepository.findById(id)).thenReturn(optionalUser);
		Exception ex = assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUserById(id));
		assertTrue(ex.getMessage().equals("User does not exist with an id " + id));
		Mockito.verify(userRepository, Mockito.times(1)).findById(id);
		Mockito.verify(userRepository, Mockito.never()).deleteById(id);
	}
}
