package eu.exadelpractice.registry.person.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.person.model.Person;

public interface PersonRepository {
	public List<Person> findAll(Map<String, String[]> map) throws BadRequestException;

	public Optional<Person> findById(String id);

	public void save(Person person);

	public void deleteById(String id);
	
	public Person findByNameSurnameDate(String name, String surname, LocalDate dateOfBirth);
}
