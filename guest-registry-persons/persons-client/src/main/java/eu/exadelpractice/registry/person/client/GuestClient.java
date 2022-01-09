package eu.exadelpractice.registry.person.client;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.model.Guest;

public interface GuestClient {
	
	Guest getGuestById(String id) throws GuestNotFoundException, InternalServerErrorException, BadRequestException;

	List<Guest> getAllGuests() throws GuestNotFoundException, InternalServerErrorException, BadRequestException;

	String createGuest(Guest guest) throws GuestNotFoundException, InternalServerErrorException, BadRequestException;

	void updateGuest(Guest guest) throws GuestNotFoundException, BadRequestException, InternalServerErrorException;

	void deleteGuestById(String id) throws GuestNotFoundException, InternalServerErrorException, BadRequestException;
}
