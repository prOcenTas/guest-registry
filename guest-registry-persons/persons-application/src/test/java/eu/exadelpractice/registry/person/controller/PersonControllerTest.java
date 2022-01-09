package eu.exadelpractice.registry.person.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.common.model.ErrorHelper;
import eu.exadelpractice.registry.person.MainTestClass;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.OperationResult;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.service.PersonService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

//@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = MainTestClass.class)
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonService personService;

	@MockBean
	private ErrorHelper he;

	private Person mockPerson = new Person("sdfadfv", "35186744635", "First", "Last", Gender.MALE, LocalDate.of(1996, 6, 24),
			new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123", "FL@example.com");

	private Person mockPerson2 = new Person("Dfasdfsf", "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");

	@Test
	public void getPersonByIdWhenPersonExists() throws Exception {
		String id = mockPerson.getId();
		//mockPerson.setId(id);
		Mockito.when(personService.getPersonById(id)).thenReturn(mockPerson);
		mockMvc.perform(MockMvcRequestBuilders.get("/persons/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.nationalId", is("35186744635"))).andExpect(jsonPath(("$.name"), is("First")))
				.andExpect(jsonPath("$.surname", is("Last"))).andExpect(jsonPath("$.gender", is("MALE")))
				.andExpect(jsonPath("$.dateOfBirth", is("24-06-1996")))
				.andExpect(jsonPath("$.address.street", is("Example st.")))
				.andExpect(jsonPath("$.address.buildingNumber", is(12)))
				.andExpect(jsonPath("$.address.apartmentNumber", is(5)))
				.andExpect(jsonPath("$.address.city", is("Madrid")))
				.andExpect(jsonPath("$.address.country", is("Spain")))
				.andExpect(jsonPath("$.nationality", is("spanish")))
				.andExpect(jsonPath("$.phoneNumber", is("+135186123")))
				.andExpect(jsonPath("$.email", is("FL@example.com")));
	}

	@Test
	public void getPersonByIdWhenPersonIsNull() throws Exception {
		String id = "Dfafdsafsdbankklkk";
		Mockito.when(personService.getPersonById(id))
				.thenThrow(new PersonNotFoundException("Person not found with an id " + id));
		mockMvc.perform(MockMvcRequestBuilders.get("/persons/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found with an id " + id,
						result.getResolvedException().getMessage()));
	}

	@Test
	public void getAllPersons() throws Exception {
		String id = mockPerson.getId();
		//mockPerson.setId(id);
		String id2 = mockPerson2.getId();
		//mockPerson2.setId(id2);
		List<Person> personList = new ArrayList<Person>();
		personList.add(mockPerson);
		personList.add(mockPerson2);
		Mockito.when(personService.findAll(Mockito.anyMap())).thenReturn(personList);
		mockMvc.perform(MockMvcRequestBuilders.get("/persons").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(id))).andExpect(jsonPath("$[0].nationalId", is("35186744635")))
				.andExpect(jsonPath(("$[0].name"), is("First"))).andExpect(jsonPath("$[0].surname", is("Last")))
				.andExpect(jsonPath("$[0].gender", is("MALE")))
				.andExpect(jsonPath("$[0].dateOfBirth", is("24-06-1996")))
				.andExpect(jsonPath("$[0].address.street", is("Example st.")))
				.andExpect(jsonPath("$[0].address.buildingNumber", is(12)))
				.andExpect(jsonPath("$[0].address.apartmentNumber", is(5)))
				.andExpect(jsonPath("$[0].address.city", is("Madrid")))
				.andExpect(jsonPath("$[0].address.country", is("Spain")))
				.andExpect(jsonPath("$[0].nationality", is("spanish")))
				.andExpect(jsonPath("$[0].phoneNumber", is("+135186123")))
				.andExpect(jsonPath("$[0].email", is("FL@example.com"))).andExpect(jsonPath("$[1].id", is(id2)))
				.andExpect(jsonPath("$[1].nationalId", is("35186767635"))).andExpect(jsonPath(("$[1].name"), is("Max")))
				.andExpect(jsonPath("$[1].surname", is("Smith"))).andExpect(jsonPath("$[1].gender", is("FEMALE")))
				.andExpect(jsonPath("$[1].dateOfBirth", is("16-12-1995")))
				.andExpect(jsonPath("$[1].address.street", is("Ex st.")))
				.andExpect(jsonPath("$[1].address.buildingNumber", is(16)))
				.andExpect(jsonPath("$[1].address.apartmentNumber", is(7)))
				.andExpect(jsonPath("$[1].address.city", is("New York")))
				.andExpect(jsonPath("$[1].address.country", is("US")))
				.andExpect(jsonPath("$[1].nationality", is("american")))
				.andExpect(jsonPath("$[1].phoneNumber", is("+135786123")))
				.andExpect(jsonPath("$[1].email", is("ms@example.com")));
	}

	@Test
	public void createPerson() throws Exception {
		String id = "Fdasfasdfdklkpi";
		Mockito.when(personService.createPerson(mockPerson)).thenReturn(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		// System.out.println(json);
		mockMvc.perform(MockMvcRequestBuilders.post("/persons").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(jsonPath("$", is(id)));
	}

	@Test
	public void createPersonWhenMandatoryFieldsNotGiven() throws Exception {
		mockPerson.setName(null);
		Mockito.when(personService.createPerson(mockPerson))
				.thenThrow(new PersonNotFoundException("Required fields not given"));
		;
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		// System.out.println(json);
		mockMvc.perform(MockMvcRequestBuilders.post("/persons").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Required fields not given",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void updatePersonWhenPersonExists() throws Exception {
		//String id = new ObjectId().toString();
		//mockPerson.setId(id);
		// Mockito.when(personService.updatePerson(mockPerson)).thenThrow(new
		// PersonNotFoundException("Person not found with an id " + id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		mockMvc.perform(MockMvcRequestBuilders.put("/persons").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void updatePersonWhenPersonDoesNotExist() throws Exception {
		String id = mockPerson.getId();
		//mockPerson.setId(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		Mockito.doThrow(new PersonNotFoundException("Person not found with an id " + id)).when(personService)
				.updatePerson(mockPerson);
		mockMvc.perform(MockMvcRequestBuilders.put("/persons").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found with an id " + id,
						result.getResolvedException().getMessage()));
	}

	@Test
	public void updatePersonWhenIdNotGiven() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		Mockito.doThrow(new PersonNotFoundException("Person id value is not given")).when(personService)
				.updatePerson(mockPerson);
		mockMvc.perform(MockMvcRequestBuilders.put("/persons").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person id value is not given",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void deletePersonByIdWhenPersonExists() throws Exception {
		String id = "dsfdsfsdabkopkljk";
		mockMvc.perform(MockMvcRequestBuilders.delete("/persons/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void deletePersonByIdWhenPersonDoesNotExist() throws Exception {
		String id = "fdasfsafdsfsdaf";
		Mockito.doThrow(new PersonNotFoundException("Person not found with an id " + id)).when(personService)
				.deletePersonById(id);
		;
		mockMvc.perform(MockMvcRequestBuilders.delete("/persons/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found with an id " + id,
						result.getResolvedException().getMessage()));
	}

	@Test
	public void findOrCreateWhenPersonExists() throws Exception {
		String id = "DFsafdsafdsfsadf";
		// mockPerson.setId(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		OperationResult op = new OperationResult(false, id);
		Mockito.when(personService.findOrCreate(mockPerson)).thenReturn(op);
		mockMvc.perform(MockMvcRequestBuilders.post("/persons/find/create").contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isOk()).andExpect(jsonPath("$", is(id)));
	}

	@Test
	public void findOrCreateWhenPersonDoesNotExist() throws Exception {
		String id = "DASfsdafsdfas";
		// mockPerson.setId(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		OperationResult op = new OperationResult(true, id);
		Mockito.when(personService.findOrCreate(mockPerson)).thenReturn(op);
		mockMvc.perform(MockMvcRequestBuilders.post("/persons/find/create").contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isCreated()).andExpect(jsonPath("$", is(id)));
	}

	@Test
	public void findOrCreateWhenValidationFails() throws Exception {
		mockPerson.setName(null);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockPerson);
		Mockito.when(personService.findOrCreate(mockPerson))
				.thenThrow(new PersonNotFoundException("Required fields are not given"));
		mockMvc.perform(MockMvcRequestBuilders.post("/persons/find/create").contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Required fields are not given",
						result.getResolvedException().getMessage()));
	}
}
