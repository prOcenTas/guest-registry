package eu.exadelpractice.registry.locations.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.common.model.ErrorHelper;
import eu.exadelpractice.registry.locations.MainTestClass;
import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.locations.model.LocationType;
import eu.exadelpractice.registry.locations.model.WorkerRef;
import eu.exadelpractice.registry.locations.service.impl.LocationServiceImpl;

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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = MainTestClass.class)
class LocationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LocationServiceImpl locationServiceImpl;

	@MockBean
	private ErrorHelper he;

	private LocationType locationType = LocationType.EVENT;
	private Address address = new Address("Gatve g.", 26, 2, "Vilnius", "Lietuva");
	private WorkerRef manager = new WorkerRef("293152", "Faustas", "Volkovas");
	private Location mockLocation = new Location("1", locationType, "Meeting with x company", address, manager);

	// private LocationType locationType2=LocationType.OFFICE;
	// private Address address2=
	// new Address("Stotis g.",10,12,"Klaipeda","Lietuva");
	// private WorkerRef manager2=new WorkerRef("293152","Jonas","Jonauskas");
	// private Location mockLocation2=new Location("2",locationType,"Klaipeda city
	// office",address2,manager2);

	@Test
	public void getLocationById() throws Exception {
		Mockito.when(locationServiceImpl.getLocation("1")).thenReturn(mockLocation);
		mockMvc.perform(MockMvcRequestBuilders.get("/locations/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.locationType", is("EVENT")))
				.andExpect(jsonPath("$.locationTitle", is("Meeting with x company")))
				.andExpect(jsonPath("$.address").value(equalTo(asParsedJson(address))))
				.andExpect(jsonPath("$.manager").value(equalTo(asParsedJson(manager))));
	}

	@Test
	public void getLocationByIdNull() throws Exception {
		String id = "6";
		Mockito.when(locationServiceImpl.getLocation(id))
				.thenThrow(new LocationNotFoundException("Location not found with id: " + id));
		mockMvc.perform(MockMvcRequestBuilders.get("/locations/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationNotFoundException))
				.andExpect(result -> assertEquals("Location not found with id: " + id,
						result.getResolvedException().getMessage()));
	}

	// konvertuoja i json i reikiama tipa, kad galetu lyginti su jsonPath (rasta
	// internete)
	private <T> T asParsedJson(Object obj) throws JsonProcessingException {
		String json = new ObjectMapper().writeValueAsString(obj);
		return JsonPath.read(json, "$");
	}

}