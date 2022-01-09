package eu.exadelpractice.registry.locations.service;

import eu.exadelpractice.registry.common.model.Address;
import eu.exadelpractice.registry.locations.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.locations.model.LocationType;
import eu.exadelpractice.registry.locations.model.WorkerRef;

import eu.exadelpractice.registry.locations.service.impl.LocationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {
	@Mock
	private LocationRepository repository;

	@InjectMocks
	private LocationServiceImpl service;

	LocationType locationType = LocationType.EVENT;
	Address address = new Address("Gatve g.", 26, 2, "Vilnius", "Lietuva");
	WorkerRef manager = new WorkerRef("293152", "Faustas", "Volkovas");
	Location mockLocation = new Location("5", locationType, "Meeting with x company", address, manager);

	@Test
	public void findById() throws Exception {
		Optional<Location> optionalMockLocation = Optional.of(mockLocation);
		Mockito.when(repository.findById("5")).thenReturn(optionalMockLocation);
		assertEquals(mockLocation, service.getLocation("5"));
	}
}