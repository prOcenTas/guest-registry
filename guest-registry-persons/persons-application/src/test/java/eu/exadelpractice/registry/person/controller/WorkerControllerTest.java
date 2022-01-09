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
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.model.WorkerType;
import eu.exadelpractice.registry.person.service.WorkerService;

//@RunWith(SpringRunner.class)
@WebMvcTest(WorkerController.class)
//@ContextConfiguration(classes = {MainTestClass.class})
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = MainTestClass.class)
public class WorkerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WorkerService workerService;

	@MockBean
	private ErrorHelper he;

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
		Mockito.when(workerService.getWorkerById(mockWorker.getId())).thenReturn(mockWorker);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		String jsonWorker = mapper.writeValueAsString(mockWorker);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/workers/" + mockWorker.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(mockWorker.getId())))
				.andExpect(MockMvcResultMatchers.content().json(jsonWorker, true));
	}

	@Test
	public void getWorkerByIdWhenWorkerIsNull() throws Exception {
		Mockito.when(workerService.getWorkerById(mockWorker.getId()))
				.thenThrow(new WorkerNotFoundException("Worker not found with an id " + mockWorker.getId()));
		mockMvc.perform(
				MockMvcRequestBuilders.get("/workers/" + mockWorker.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof WorkerNotFoundException))
				.andExpect(result -> assertEquals("Worker not found with an id " + mockWorker.getId(),
						result.getResolvedException().getMessage()));
	}

	@Test
	public void getAllWorkers() throws Exception {
		List<Worker> workerList = new ArrayList<Worker>();
		workerList.add(mockWorker);
		workerList.add(mockWorker2);
		Mockito.when(workerService.findAll()).thenReturn(workerList);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		String jsonWorker = mapper.writeValueAsString(workerList);
		mockMvc.perform(MockMvcRequestBuilders.get("/workers").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(2)))
				.andExpect(MockMvcResultMatchers.content().json(jsonWorker, true));
	}

	@Test
	public void createWorker() throws Exception {
		String id = "fdasfdsafdf";
		Mockito.when(workerService.createWorker(mockWorker)).thenReturn(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockWorker);
		// System.out.println(json);
		mockMvc.perform(MockMvcRequestBuilders.post("/workers").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(jsonPath("$", is(id)));
	}

	@Test
	public void createWorkerWhenPersonDoesNotExist() throws Exception {
		mockPerson.setName(null);
		Mockito.when(workerService.createWorker(mockWorker))
				.thenThrow(new PersonNotFoundException("Person does not exist"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockWorker);
		mockMvc.perform(MockMvcRequestBuilders.post("/workers").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person does not exist", result.getResolvedException().getMessage()));
	}

	@Test
	public void updateWorkerWhenNoException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockWorker);
		mockMvc.perform(MockMvcRequestBuilders.put("/workers").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void updateWorkerWhenPersonDoesNotExist() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockWorker);

		Mockito.doThrow(new PersonNotFoundException("Person does not exist")).when(workerService)
				.updateWorker(mockWorker);
		mockMvc.perform(MockMvcRequestBuilders.put("/workers").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person does not exist", result.getResolvedException().getMessage()));
	}

	/*@Test
	public void updateWorkerWhenLocationDoesNotExist() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockWorker);

		Mockito.doThrow(new LocationNotFoundException("Location does not exist")).when(workerService)
				.updateWorker(mockWorker);
		mockMvc.perform(MockMvcRequestBuilders.put("/workers").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationNotFoundException))
				.andExpect(
						result -> assertEquals("Location does not exist", result.getResolvedException().getMessage()));
	}*/

	@Test
	public void deleteWorkerByIdWhenWorkerExists() throws Exception {
		String id = "dfasfdsa";
		mockMvc.perform(MockMvcRequestBuilders.delete("/workers/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void deleteWorkerByIdWhenWorkerDoesNotExist() throws Exception {
		String id = "dfdasf";
		Mockito.doThrow(new WorkerNotFoundException("Worker not found with an id " + id)).when(workerService)
				.deleteWorkerById(id);
		mockMvc.perform(MockMvcRequestBuilders.delete("/workers/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof WorkerNotFoundException))
				.andExpect(result -> assertEquals("Worker not found with an id " + id,
						result.getResolvedException().getMessage()));
	}
}
