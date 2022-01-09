package eu.exadelpractice.registry.locations.controller;

import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.exception.WorkerFieldBadValueException;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.locations.service.impl.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

	private final LocationServiceImpl locationServiceImpl;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void saveLocation(@RequestBody Location location) {
		locationServiceImpl.saveLocation(location);
	}

	@GetMapping
	public List<Location> getLocations() {
		return locationServiceImpl.getAll();
	}

	@GetMapping("{id}")
	public Location getLocation(@PathVariable String id) throws LocationNotFoundException {
		return locationServiceImpl.getLocation(id);
	}

	@GetMapping("/type/{locationType}")
	public List<Location> getLocationByType(@PathVariable String locationType) {
		return locationServiceImpl.filterType(locationType);
	}

	@GetMapping("/city/{city}")
	public List<Location> getLocationByCity(@PathVariable String city) {
		return locationServiceImpl.filterCity(city);
	}

	@GetMapping("/country/{country}")
	public List<Location> getLocationByCountry(@PathVariable String country) {
		return locationServiceImpl.filterCountry(country);
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateLocation(@RequestBody Location location) throws LocationNotFoundException, WorkerFieldBadValueException {
		locationServiceImpl.updateLocation(location);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deleteLocation(@PathVariable String id) throws LocationNotFoundException {
		locationServiceImpl.deleteLocation(id);
	}
}
