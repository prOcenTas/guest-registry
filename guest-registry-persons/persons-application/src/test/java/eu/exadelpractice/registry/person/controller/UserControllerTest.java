package eu.exadelpractice.registry.person.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.common.model.ErrorHelper;
import eu.exadelpractice.registry.person.MainTestClass;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Role;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.service.UserService;

//@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
//@ContextConfiguration(classes = {MainTestClass.class})
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = MainTestClass.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private ErrorHelper he;

	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");
	private User mockUser = new User("fdasbvdfbfs", mockPerson, "1351215", Role.ADMIN);

	private User mockUser2 = new User("DSAFVXZcva", mockPerson, "135145123115", Role.USER);

	/*
	 * private Person mockPerson2 = new Person("513dsaf", "35186767635", "Max",
	 * "Smith", Gender.FEMALE, LocalDate.of(1995, 12, 16), new Address("Ex st.", 16,
	 * 7, "New York", "US"), "american", "+135786123", "ms@example.com");
	 */

	@Test
	public void getUserByIdWhenUserExists() throws Exception {
		Mockito.when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		String jsonUser = mapper.writeValueAsString(mockUser);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/users/" + mockUser.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(mockUser.getId())))
				.andExpect(MockMvcResultMatchers.content().json(jsonUser, true));
	}

	@Test
	public void getUserByIdWhenUserIsNull() throws Exception {
		Mockito.when(userService.getUserById(mockUser.getId()))
				.thenThrow(new UserNotFoundException("User not found with an id " + mockUser.getId()));
		mockMvc.perform(
				MockMvcRequestBuilders.get("/users/" + mockUser.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
				.andExpect(result -> assertEquals("User not found with an id " + mockUser.getId(),
						result.getResolvedException().getMessage()));
	}

	@Test
	public void findAllUsers() throws Exception {
		List<User> userList = new ArrayList<User>();
		userList.add(mockUser);
		userList.add(mockUser2);
		Mockito.when(userService.findAll()).thenReturn(userList);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		String jsonUser = mapper.writeValueAsString(userList);
		mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(2)))
				.andExpect(MockMvcResultMatchers.content().json(jsonUser, true));
	}

	@Test
	public void createUser() throws Exception {
		String id = "fdasfdsafdf";
		Mockito.when(userService.createUser(mockUser)).thenReturn(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockUser);
		// System.out.println(json);
		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(jsonPath("$", is(id)));
	}

	@Test
	public void createUserWhenPersonDoesNotExist() throws Exception {
		mockPerson.setName(null);
		Mockito.when(userService.createUser(mockUser)).thenThrow(new PersonNotFoundException("Person does not exist"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockUser);
		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person does not exist", result.getResolvedException().getMessage()));
	}

	@Test
	public void updateUserWhenNoException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockUser);
		mockMvc.perform(MockMvcRequestBuilders.put("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void updateUserWhenPersonDoesNotExist() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockUser);

		Mockito.doThrow(new PersonNotFoundException("Person does not exist")).when(userService).updateUser(mockUser);
		mockMvc.perform(MockMvcRequestBuilders.put("/users").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person does not exist", result.getResolvedException().getMessage()));
	}

	@Test
	public void deleteUserByIdWhenUserExists() throws Exception {
		String id = "dfasfdsa";
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void deleteUserByIdWhenUserDoesNotExist() throws Exception {
		String id = "dfdasf";
		Mockito.doThrow(new UserNotFoundException("User not found with an id " + id)).when(userService)
				.deleteUserById(id);
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
				.andExpect(result -> assertEquals("User not found with an id " + id,
						result.getResolvedException().getMessage()));
	}
}
