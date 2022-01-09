package eu.exadelpractice.registry.locations;

import eu.exadelpractice.registry.locations.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    public List<Location> findAll();

    public Optional<Location> findById(String id);

    public void save(Location location);

    public void deleteById(String id);

    public List<Location> findByType(String locationType);

    public List<Location> findByCity(String city);

    public List<Location> findByCountry(String country);
}
