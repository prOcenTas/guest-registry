package eu.exadelpractice.registry.person.validator;

import java.util.Optional;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import eu.exadelpractice.registry.person.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerValidator {
	private final WorkerRepository workerRepository;
	private final PersonRepository personRepository;
	//private final LocationRepository locationRepository;
	private final PersonValidator personValidator;
	//private final LocationValidator locationValidator;
	//private final CardRepository cardRepository;
	//private final CardValidator cardValidator;

	public void id(Worker w) throws WorkerNotFoundException {
		if (w.getId() == null) {
			throw new WorkerNotFoundException("Worker id value is not given");
		}
	}

	public void validateNewWorker(Worker w) throws ObjectNotFoundException {
		if (w.getPerson() == null) {
			throw new PersonNotFoundException("Person not given");
		}
		personValidator.id(w.getPerson());
		Optional<Person> optionalPerson = personRepository.findById(w.getPerson().getId());
		if (optionalPerson.isEmpty()) {
			throw new PersonNotFoundException("Person does not exist");
		}
		Worker worker = workerRepository.findByPerson_id(w.getPerson().getId());
		if (worker != null) {
			if (!worker.getId().equals(w.getId())) {
				throw new WorkerNotFoundException("This worker already exists");
			}
		}
		/*locationValidator.id(w.getLocationOfOffice());
		if (locationRepository.findById(w.getLocationOfOffice().getLocationId()).isEmpty()) {
			throw new LocationNotFoundException("Location does not exist");
		}
		cardValidator.id(w.getCard());
		if (cardRepository.findById(w.getCard().getId()).isEmpty()) {
			throw new CardNotFoundException("Card does not exist");
		}*/
	}
}
