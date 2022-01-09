package eu.exadelpractice.registry.person.client.impl;

import java.util.List;

import eu.exadelpractice.registry.common.client.GenericClientImpl;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import eu.exadelpractice.registry.person.client.UserClient;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.User;

@Service
//@RequiredArgsConstructor
public class UserClientImpl extends GenericClientImpl<User, UserNotFoundException> implements UserClient {

	public UserClientImpl() {
		super(User.class, "users/");
	}

//	@PostConstruct
//    private void postConstruct() {
//        genericImpl.setType(User.class);
//        genericImpl.setExceptionType(UserNotFoundException.class);
//    }
	
	@Override
	public User getUserById(String id) throws UserNotFoundException, InternalServerErrorException, BadRequestException {
		return this.getObjectById(id);
	}

	@Override
	public List<User> getAllUsers() throws UserNotFoundException, InternalServerErrorException, BadRequestException  {
		return this.getAllObjects(User[].class);
	}

	@Override
	public String createUser(User user) throws UserNotFoundException, InternalServerErrorException, BadRequestException {
		return this.createObject(user);
	}

	@Override
	public void updateUser(User user) throws UserNotFoundException, BadRequestException, InternalServerErrorException {
		this.updateObject(user);
	}

	@Override
	public void deleteUserById(String id) throws UserNotFoundException, InternalServerErrorException, BadRequestException {
		this.deleteObjectById(id);
	}

	@Override
	protected UserNotFoundException createDomainNotFoundException(HttpClientErrorException ex) {
		return new UserNotFoundException(ex.getMessage());
	}
}
