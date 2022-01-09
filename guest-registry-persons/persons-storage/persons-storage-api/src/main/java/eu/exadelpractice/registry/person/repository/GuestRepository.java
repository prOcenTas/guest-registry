package eu.exadelpractice.registry.person.repository;

import eu.exadelpractice.registry.person.model.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestRepository {

	public List<Guest> findAll();

	public Optional<Guest> findById(String id);

	public void save(Guest guest);

	public void deleteById(String id);
}
