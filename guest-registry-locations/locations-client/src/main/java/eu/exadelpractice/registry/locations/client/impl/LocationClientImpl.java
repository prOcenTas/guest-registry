package eu.exadelpractice.registry.locations.client.impl;

import eu.exadelpractice.registry.common.client.GenericClientImpl;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.locations.client.LocationClient;
import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.model.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class LocationClientImpl extends GenericClientImpl<Location, LocationNotFoundException> implements LocationClient {


    public LocationClientImpl() {
        super(Location.class, "locations/");
    }

    private final Map<String, Location> cach = new HashMap<>();


    @Override
    public Location getLocationById(String id) throws LocationNotFoundException, InternalServerErrorException, BadRequestException {
        return getFromCash(id);
    }

    private Location getFromCash(String id) throws LocationNotFoundException, InternalServerErrorException, BadRequestException {
        Location cached = cach.get(id);
        if (cached == null) {
            cached = getObjectById(id);
            cach.put(id, cached);
        }

        return cached;
    }

    @Override
    public List<Location> getAllLocations() throws LocationNotFoundException, InternalServerErrorException, BadRequestException {
        return this.getAllObjects(Location[].class);
    }

    @Override
    public String saveLocation(Location location) throws LocationNotFoundException, InternalServerErrorException, BadRequestException {
        return this.createObject(location);
    }

    @Override
    public void updateLocation(Location location) throws LocationNotFoundException, BadRequestException, InternalServerErrorException {
        this.updateObject(location);
    }

    @Override
    public void deleteLocation(String id) throws LocationNotFoundException, InternalServerErrorException, BadRequestException {
        this.deleteObjectById(id);
    }

	@Override
	protected LocationNotFoundException createDomainNotFoundException(HttpClientErrorException ex) {
		return new LocationNotFoundException(ex.getMessage());
	}

}
