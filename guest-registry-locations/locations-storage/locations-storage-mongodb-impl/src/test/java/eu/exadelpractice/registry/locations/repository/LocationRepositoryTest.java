package eu.exadelpractice.registry.locations.repository;


import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.locations.LocationRepository;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.locations.model.LocationType;
import eu.exadelpractice.registry.locations.model.WorkerRef;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
//@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan(basePackages = {"eu.exadelpractice.registry.locations"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LocationRepositoryTest {

	@Autowired
	private LocationRepository locationRepository;

	private LocationType locationType = LocationType.EVENT;
	private Address address = new Address("Gatve g.", 26, 2, "Vilnius", "Lietuva");
	private WorkerRef manager = new WorkerRef("293152", "Faustas", "Volkovas");
	private Location mockLocation = new Location("1", locationType, "Meeting with x company", address, manager);

	private LocationType locationType2 = LocationType.OFFICE;
	private Address address2 = new Address("Stotis g.", 10, 12, "Klaipeda", "Lietuva");
	private WorkerRef manager2 = new WorkerRef("293152", "Jonas", "Jonauskas");
	private Location mockLocation2 = new Location("2", locationType2, "Klaipeda city office", address2, manager2);

	@Test
	public void findById() throws Exception {
		locationRepository.save(mockLocation);
		Optional<Location> optionalLocation = locationRepository.findById("1");
		assertTrue(optionalLocation.isPresent());
		Location location = optionalLocation.orElseThrow();

		assertEquals(mockLocation.getLocationId(), location.getLocationId());
		assertEquals(mockLocation.getLocationType(), location.getLocationType());
		assertEquals(mockLocation.getLocationTitle(), location.getLocationTitle());
		assertEquals(mockLocation.getAddress(), location.getAddress());
		assertEquals(mockLocation.getManager(), location.getManager());

		Optional<Location> optionalNullLocation = locationRepository.findById("888");
		assertTrue(optionalNullLocation.isEmpty());
	}

	@Test
	public void findByIdNull() throws Exception {
		String id = "6";
		Optional<Location> optionalLocation = locationRepository.findById(id);
		assertTrue(optionalLocation.isEmpty());
	}

	@Test
	public void deleteById() throws Exception {
		locationRepository.save(mockLocation);
		Optional<Location> optionalLocation = locationRepository.findById("1");
		assertTrue(optionalLocation.isPresent());

		locationRepository.deleteById("1");
		optionalLocation = locationRepository.findById("1");
		assertTrue(optionalLocation.isEmpty());
	}

	@Test
	public void findByCity() throws Exception {
		String city = "Vilnius";
		locationRepository.save(mockLocation);
		locationRepository.save(mockLocation2);
		List<Location> actual = locationRepository.findByCity(city);
		List<Location> expected = new ArrayList<>();
		expected.add(mockLocation);

		assertEquals(expected, actual);
	}

	@Test
	public void findByCountry() throws Exception {
		String country = "Lietuva";
		locationRepository.save(mockLocation);
		locationRepository.save(mockLocation2);
		List<Location> actual = locationRepository.findByCountry(country);
		List<Location> expected = new ArrayList<>();
		expected.add(mockLocation);
		expected.add(mockLocation2);

		assertEquals(expected, actual);
	}

	@Test
	public void findByType() throws Exception {
		// LocationType type=LocationType.EVENT;
		locationRepository.save(mockLocation);
		locationRepository.save(mockLocation2);
		List<Location> actual = locationRepository.findByType("EVENT");
		List<Location> expected = new ArrayList<>();
		System.out.println(actual);
		expected.add(mockLocation);

		assertEquals(expected, actual);
	}
}