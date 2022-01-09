package eu.exadelpractice.registry.person.service;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.User;

public interface UserService {

	public List<User> findAll();

	public String createUser(User user) throws ObjectNotFoundException;

	public User getUserById(String id) throws UserNotFoundException;

	public void updateUser(User user) throws ObjectNotFoundException;

	public void deleteUserById(String id) throws UserNotFoundException;
}
