package eu.exadelpractice.registry.person.client;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.User;

public interface UserClient {
	User getUserById(String id) throws UserNotFoundException, InternalServerErrorException, BadRequestException;

	List<User> getAllUsers() throws UserNotFoundException, InternalServerErrorException, BadRequestException;

	String createUser(User user) throws UserNotFoundException, InternalServerErrorException, BadRequestException;

	void updateUser(User user) throws UserNotFoundException, BadRequestException, InternalServerErrorException;

	void deleteUserById(String id) throws UserNotFoundException, InternalServerErrorException, BadRequestException;
}
