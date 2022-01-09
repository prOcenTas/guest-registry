package eu.exadelpractice.registry.locations.service;

import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.exception.WorkerFieldBadValueException;
import eu.exadelpractice.registry.locations.model.Location;

import java.util.List;

public interface LocationService {
	public void saveLocation(Location location);

	public Location getLocation(String id) throws LocationNotFoundException;

	public List<Location> getAll();

	public List<Location> filterType(String locationType);

	public List<Location> filterCity(String city);

	public List<Location> filterCountry(String country);

	public void updateLocation(Location location) throws LocationNotFoundException, WorkerFieldBadValueException;

	public void deleteLocation(String id) throws LocationNotFoundException;
}
