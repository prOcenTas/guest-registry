package eu.exadelpractice.registry.person.service;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.OperationResult;
import eu.exadelpractice.registry.person.model.Person;

import java.util.List;
import java.util.Map;

public interface PersonService {

	public List<Person> findAll(Map<String, String[]> map) throws BadRequestException;

	public String createPerson(Person person) throws PersonNotFoundException;

	public Person getPersonById(String id) throws PersonNotFoundException;

	public void updatePerson(Person person) throws PersonNotFoundException;

	public void deletePersonById(String id) throws PersonNotFoundException;

	public OperationResult findOrCreate(Person p) throws PersonNotFoundException;
}
