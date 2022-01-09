package eu.exadelpractice.registry.person.service;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.model.Guest;

import java.util.List;

public interface GuestService {

	public List<Guest> findAll();

	public String createGuest(Guest guest) throws ObjectNotFoundException;

	public Guest getGuestById(String id) throws GuestNotFoundException;

	public void updateGuest(Guest guest) throws ObjectNotFoundException;

	public void deleteGuestById(String id) throws GuestNotFoundException;
}
