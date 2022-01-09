package eu.exadelpractice.registry.person.validator;

import java.util.Optional;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.GuestNotFoundException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestValidator {
	private final PersonValidator personValidator;
	private final PersonRepository personRepository;
	//private final LocationRepository locationRepository;
	//private final LocationValidator locationValidator;
	//private final CardRepository cardRepository;
	//private final CardValidator cardValidator;

	public void validateNewGuest(Guest guest) throws ObjectNotFoundException {
		if (guest.getPerson() == null) {
			throw new PersonNotFoundException("Person not given");
		}
		personValidator.id(guest.getPerson());
		Optional<Person> optionalPerson = personRepository.findById(guest.getPerson().getId());
		if (optionalPerson.isEmpty()) {
			throw new PersonNotFoundException("Person does not exist");
		}
		/*
		locationValidator.id(guest.getLocation());
		if (locationRepository.findById(guest.getLocation().getLocationId()).isEmpty()) {
			throw new LocationNotFoundException("Location does not exist");
		}
		cardValidator.id(guest.getCard());
		if (cardRepository.findById(guest.getCard().getId()).isEmpty()) {
			throw new CardNotFoundException("Card does not exist");
		}*/

	}

	public void id(Guest guest) throws GuestNotFoundException {
		if (guest.getId() == null) {
			throw new GuestNotFoundException("Guest id value is not given");
		}
	}

}
