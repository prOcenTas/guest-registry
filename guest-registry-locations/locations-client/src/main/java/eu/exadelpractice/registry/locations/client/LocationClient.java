package eu.exadelpractice.registry.locations.client;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.model.Location;

import java.util.List;

public interface LocationClient {
    public Location getLocationById(String id) throws LocationNotFoundException, InternalServerErrorException, BadRequestException;

    List<Location> getAllLocations() throws LocationNotFoundException, InternalServerErrorException, BadRequestException;

    String saveLocation(Location location) throws LocationNotFoundException, InternalServerErrorException, BadRequestException;

    void updateLocation(Location location) throws LocationNotFoundException, BadRequestException, InternalServerErrorException;

    void deleteLocation(String id) throws LocationNotFoundException, InternalServerErrorException, BadRequestException;
}
