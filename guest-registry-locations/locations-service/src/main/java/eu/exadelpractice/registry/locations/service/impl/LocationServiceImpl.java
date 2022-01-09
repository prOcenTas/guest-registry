package eu.exadelpractice.registry.locations.service.impl;

import eu.exadelpractice.registry.locations.LocationRepository;
import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.exception.WorkerFieldBadValueException;
import eu.exadelpractice.registry.locations.model.Location;
import eu.exadelpractice.registry.locations.service.LocationService;
import eu.exadelpractice.registry.locations.validator.LocationValidator;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

	private final LocationRepository locationRepository;
	private final LocationValidator validator;

	@Override
	public void saveLocation(Location location) {
		if(location.getLocationId() == null){
			location.setLocationId(new ObjectId().toString());
		}
		locationRepository.save(location);
	}

	@Override
	public Location getLocation(String id) throws LocationNotFoundException {
		return locationRepository.findById(id)
				.orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));
	}

	@Override
	public List<Location> getAll() {
		return locationRepository.findAll();
	}

	@Override
	public List<Location> filterType(String locationType) {
		return locationRepository.findByType(locationType);
	}

	@Override
	public List<Location> filterCity(String city) {
		return locationRepository.findByCity(city);
	}

	@Override
	public List<Location> filterCountry(String country) {
		return locationRepository.findByCountry(country);
	}

	@Override
	public void updateLocation(Location location) throws LocationNotFoundException, WorkerFieldBadValueException {
		validator.id(location);
		this.getLocation(location.getLocationId());
		locationRepository.save(location);
	}

	@Override
	public void deleteLocation(String id) throws LocationNotFoundException {
		if (this.getLocation(id) == null) {
			throw new LocationNotFoundException("Id was not provided or found");
		}
		locationRepository.deleteById(id);
	}
}
