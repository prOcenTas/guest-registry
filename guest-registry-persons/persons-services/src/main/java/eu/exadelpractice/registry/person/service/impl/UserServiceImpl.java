package eu.exadelpractice.registry.person.service.impl;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.repository.UserRepository;
import eu.exadelpractice.registry.person.service.UserService;
import eu.exadelpractice.registry.person.validator.UserValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserValidator userValidator;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public String createUser(User user) throws ObjectNotFoundException {
		user.setId(null);
		userValidator.validateNewUser(user);
		userRepository.save(user);
		return user.getId();
	}

	@Override
	public User getUserById(String id) throws UserNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User does not exist with an id " + id));
	}

	@Override
	public void updateUser(User user) throws ObjectNotFoundException {
		userValidator.id(user);
		userValidator.validateNewUser(user);
		this.getUserById(user.getId());
		userRepository.save(user);
	}

	@Override
	public void deleteUserById(String id) throws UserNotFoundException {
		this.getUserById(id);
		userRepository.deleteById(id);
	}

}
