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
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Gender;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.model.GuestType;
import eu.exadelpractice.registry.person.model.LocationRef;
import eu.exadelpractice.registry.person.model.CardRef;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.service.GuestService;
import org.springframework.security.test.context.support.WithMockUser;

//@RunWith(SpringRunner.class)
@WebMvcTest(GuestController.class)
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = MainTestClass.class)
public class GuestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GuestService guestService;

	@MockBean
	private ErrorHelper he;

	private Person mockPerson = new Person("dfasfsdfsadf", "35186744635", "First", "Last", Gender.MALE,
			LocalDate.of(1996, 6, 24), new Address("Example st.", 12, 5, "Madrid", "Spain"), "spanish", "+135186123",
			"FL@example.com");

	private Person mockPerson2 = new Person("513dsaf", "35186767635", "Max", "Smith", Gender.FEMALE,
			LocalDate.of(1995, 12, 16), new Address("Ex st.", 16, 7, "New York", "US"), "american", "+135786123",
			"ms@example.com");

	//private LocationType locationType = LocationType.EVENT;
	//private Address address = new Address("Gatve g.", 26, 2, "Vilnius", "Lietuva");
	//private WorkerRef manager = new WorkerRef("293152", "Faustas", "Volkovas");
	//private Location mockLocation = new Location("1", locationType, "Meeting with x company", address, manager);
	//private PersonRef person = new PersonRef("dfasfsdfsadf", "First", "Last");
	private LocationRef location = new LocationRef("1", "Meeting with x company");
	private CardRef card = new CardRef("DSfdsafdfvnmmn", "worker card");
	private Guest mockGuest = new Guest("dsafdsfvcxc", mockPerson, card, "Exadel", "Good", location, "noreason",
			GuestType.GUEST);
	private Guest mockGuest2 = new Guest("xzcvxcv", mockPerson2, card, "Google", "Wed", location, "good reason",
			GuestType.VISITOR);

	@Test
	public void getGuestByIdWhenGuestExists() throws Exception {
		Mockito.when(guestService.getGuestById(mockGuest.getId())).thenReturn(mockGuest);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		String jsonGuest = mapper.writeValueAsString(mockGuest);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/guests/" + mockGuest.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(mockGuest.getId())))
				.andExpect(MockMvcResultMatchers.content().json(jsonGuest, true));
	}

	@Test
	public void getGuestByIdWhenGuestIsNull() throws Exception {
		Mockito.when(guestService.getGuestById(mockGuest.getId()))
				.thenThrow(new GuestNotFoundException("Guest not found with an id " + mockGuest.getId()));
		mockMvc.perform(
				MockMvcRequestBuilders.get("/guests/" + mockGuest.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof GuestNotFoundException))
				.andExpect(result -> assertEquals("Guest not found with an id " + mockGuest.getId(),
						result.getResolvedException().getMessage()));
	}

	@Test
	public void getAllGuests() throws Exception {
		List<Guest> guestList = new ArrayList<Guest>();
		guestList.add(mockGuest);
		guestList.add(mockGuest2);
		Mockito.when(guestService.findAll()).thenReturn(guestList);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.registerModule(new JavaTimeModule());
		String jsonGuest = mapper.writeValueAsString(guestList);
		mockMvc.perform(MockMvcRequestBuilders.get("/guests").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(2)))
				.andExpect(MockMvcResultMatchers.content().json(jsonGuest, true));
	}

	@Test
	public void createGuest() throws Exception {
		String id = "fdasfdsafdf";
		Mockito.when(guestService.createGuest(mockGuest)).thenReturn(id);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockGuest);
		// System.out.println(json);
		mockMvc.perform(MockMvcRequestBuilders.post("/guests").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(jsonPath("$", is(id)));
	}

	@Test
	public void createGuestWhenPersonDoesNotExist() throws Exception {
		mockPerson.setName(null);
		Mockito.when(guestService.createGuest(mockGuest))
				.thenThrow(new PersonNotFoundException("Person does not exist"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockGuest);
		mockMvc.perform(MockMvcRequestBuilders.post("/guests").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person does not exist", result.getResolvedException().getMessage()));
	}

	@Test
	public void updateGuestWhenNoException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockGuest);
		mockMvc.perform(MockMvcRequestBuilders.put("/guests").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void updateGuestWhenPersonDoesNotExist() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockGuest);

		Mockito.doThrow(new PersonNotFoundException("Person does not exist")).when(guestService).updateGuest(mockGuest);
		mockMvc.perform(MockMvcRequestBuilders.put("/guests").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person does not exist", result.getResolvedException().getMessage()));
	}

	/*@Test
	public void updateGuestWhenLocationDoesNotExist() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		String json = mapper.writeValueAsString(mockGuest);

		Mockito.doThrow(new LocationNotFoundException("Location does not exist")).when(guestService)
				.updateGuest(mockGuest);
		mockMvc.perform(MockMvcRequestBuilders.put("/guests").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationNotFoundException))
				.andExpect(
						result -> assertEquals("Location does not exist", result.getResolvedException().getMessage()));
	}*/

	@Test
	public void deleteGuestByIdWhenGuestExists() throws Exception {
		String id = "dfasfdsa";
		mockMvc.perform(MockMvcRequestBuilders.delete("/guests/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void deleteGuestByIdWhenGuestDoesNotExist() throws Exception {
		String id = "dfdasf";
		Mockito.doThrow(new GuestNotFoundException("Guest not found with an id " + id)).when(guestService)
				.deleteGuestById(id);
		mockMvc.perform(MockMvcRequestBuilders.delete("/guests/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof GuestNotFoundException))
				.andExpect(result -> assertEquals("Guest not found with an id " + id,
						result.getResolvedException().getMessage()));
	}

}
