package eu.exadelpractice.registry.person.validator;

import java.util.Optional;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.exception.UserNotFoundException;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import eu.exadelpractice.registry.person.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidator {
	// private final PersonValidator personValidator;
	private final PersonRepository personRepository;
	private final UserRepository userRepository;

	public void validateNewUser(User user) throws ObjectNotFoundException {
		if (user.getPerson() == null) {
			throw new PersonNotFoundException("Person not given");
		}
		if (user.getPerson().getId() == null) {
			throw new PersonNotFoundException("Person not given");
		}
		Optional<Person> optionalPerson = personRepository.findById(user.getPerson().getId());
		if (optionalPerson.isEmpty()) {
			throw new PersonNotFoundException("Person does not exist");
		}
		User u = userRepository.findByPerson_id(user.getPerson().getId());
		if (u != null) {
			if (!u.getId().equals(user.getId())) {
				throw new UserNotFoundException("This user already exists");
			}
		}
	}

	public void id(User user) throws UserNotFoundException {
		if (user.getId() == null) {
			throw new UserNotFoundException("User id value is not given");
		}
	}

}
