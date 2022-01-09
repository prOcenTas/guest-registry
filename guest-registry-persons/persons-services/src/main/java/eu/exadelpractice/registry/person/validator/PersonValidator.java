package eu.exadelpractice.registry.person.validator;

import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonValidator {

	private final PersonRepository personRepository;

	public void mandatoryFields(Person p) throws PersonNotFoundException {
		if (p.getName() == null || p.getSurname() == null || p.getDateOfBirth() == null) {
			throw new PersonNotFoundException("Required fields are not given");
		}
	}

	public void id(Person p) throws PersonNotFoundException {
		if (p.getId() == null) {
			throw new PersonNotFoundException("Person id value is not given");
		}
	}

	public void validateNewPerson(Person p) throws PersonNotFoundException {
		
		this.mandatoryFields(p);
		Person person = personRepository.findByNameSurnameDate(p.getName(), p.getSurname(), p.getDateOfBirth());
		if (person != null && !person.getId().equals(p.getId())) {
			throw new PersonNotFoundException("This person is already created");
		}
	}
}
