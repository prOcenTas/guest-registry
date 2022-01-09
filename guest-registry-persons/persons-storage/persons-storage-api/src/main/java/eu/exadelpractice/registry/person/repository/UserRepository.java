package eu.exadelpractice.registry.person.repository;

import java.util.List;
import java.util.Optional;

import eu.exadelpractice.registry.person.model.User;

public interface UserRepository {

	public List<User> findAll();

	public Optional<User> findById(String id);

	public void save(User user);

	public void deleteById(String id);
	
	public User findByPerson_id(String id);
}
