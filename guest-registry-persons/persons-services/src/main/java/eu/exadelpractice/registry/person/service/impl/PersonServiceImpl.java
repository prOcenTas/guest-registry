package eu.exadelpractice.registry.person.service.impl;

import java.util.List;
import java.util.Map;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.OperationResult;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.repository.PersonRepository;
import eu.exadelpractice.registry.person.service.PersonService;
import eu.exadelpractice.registry.person.validator.PersonValidator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;

	private final PersonValidator personValidator;

	/*
	 * public PersonServiceImpl(PersonRepository personRepository) {
	 * this.personRepository = personRepository; }
	 */
	@Override
	public List<Person> findAll(Map<String, String[]> map) throws BadRequestException {
		List<Person> persons = personRepository.findAll(map);
		/*Stream<Person> stream = persons.stream();
		for (String key : map.keySet()) {
			String[] nlog = key.split("\\.");
			if (nlog.length != 2) {
				throw new BadRequestException("The format of request path is bad");
			} else {
				switch (nlog[0]) {
				case "name":
					if (!nlog[1].equals("contains") && !nlog[1].equals("equals") && !nlog[1].equals("notEquals")
							&& !nlog[1].equals("startsWith")) {
						throw new BadRequestException("The format of request path is bad");
					}
					for (String val : map.get(key)) {
						stream = stream.filter(p -> (nlog[1].equals("contains") && p.getName().contains(val))
								|| (nlog[1].equals("startsWith") && p.getName().startsWith(val))
								|| (nlog[1].equals("equals") && p.getName().equals(val))
								|| (nlog[1].equals("notEquals") && !p.getName().equals(val)));
					}
					break;
				case "surname":
					if (!nlog[1].equals("contains") && !nlog[1].equals("equals") && !nlog[1].equals("notEquals")
							&& !nlog[1].equals("startsWith")) {
						throw new BadRequestException("The format of request path is bad");
					}
					for (String val : map.get(key)) {
						stream = stream.filter(p -> (nlog[1].equals("contains") && p.getSurname().contains(val))
								|| (nlog[1].equals("startsWith") && p.getSurname().startsWith(val))
								|| (nlog[1].equals("equals") && p.getSurname().equals(val))
								|| (nlog[1].equals("notEquals") && !p.getSurname().equals(val)));
					}
					break;
				case "birthDate":
					if (!nlog[1].equals("gt") && !nlog[1].equals("equals") && !nlog[1].equals("notEquals")
							&& !nlog[1].equals("lt") && !nlog[1].equals("lte") && !nlog[1].equals("gte")) {
						throw new BadRequestException("The format of request path is bad");
					}
					for (String value : map.get(key)) {
						String[] arr = value.split("\\-");
						if (arr.length != 3) {
							throw new BadRequestException("The format of request path is bad");
						}
						LocalDate val = LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[1]),
								Integer.parseInt(arr[0]));
						stream = stream.filter(p -> (nlog[1].equals("gt") && p.getDateOfBirth().isAfter(val))
								|| (nlog[1].equals("gte")
										&& (p.getDateOfBirth().isAfter(val) || p.getDateOfBirth().isEqual(val)))
								|| (nlog[1].equals("equals") && p.getDateOfBirth().isEqual(val))
								|| (nlog[1].equals("notEquals") && !p.getDateOfBirth().isEqual(val))
								|| (nlog[1].equals("lt") && p.getDateOfBirth().isBefore(val)) || (nlog[1].equals("lte")
										&& (p.getDateOfBirth().isBefore(val) || p.getDateOfBirth().isEqual(val))));
					}
					break;
				default:
					throw new BadRequestException("The format of request path is bad");
				}
			}
		}
		persons = stream.collect(Collectors.toList());*/
		return persons;
	}

	@Override
	public String createPerson(Person person) throws PersonNotFoundException {
		person.setId(null);
		personValidator.validateNewPerson(person);
		personRepository.save(person);
		return person.getId();
	}

	@Override
	public Person getPersonById(String id) throws PersonNotFoundException {
		return personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException("Person not found with an id " + id));
	}

	@Override
	public void updatePerson(Person person) throws PersonNotFoundException {
		personValidator.id(person);
		personValidator.validateNewPerson(person);
		this.getPersonById(person.getId());
		// p.copyFields(person);
		personRepository.save(person);
	}

	@Override
	public void deletePersonById(String id) throws PersonNotFoundException {
		this.getPersonById(id);
		personRepository.deleteById(id);
	}

	@Override
	public OperationResult findOrCreate(Person p) throws PersonNotFoundException {
		personValidator.mandatoryFields(p);
		Person person = personRepository.findByNameSurnameDate(p.getName(), p.getSurname(), p.getDateOfBirth());
		if (person != null) {
			return new OperationResult(false, person.getId());
		} else {
			return new OperationResult(true, createPerson(p));
		}
	}

}
