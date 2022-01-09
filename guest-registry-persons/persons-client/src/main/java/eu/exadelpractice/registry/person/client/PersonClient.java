package eu.exadelpractice.registry.person.client;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import org.springframework.http.ResponseEntity;

import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Person;

public interface PersonClient {
	Person getPersonById(String id) throws PersonNotFoundException, InternalServerErrorException, BadRequestException;

	List<Person> getAllPersons() throws PersonNotFoundException, InternalServerErrorException, BadRequestException;

	String createPerson(Person person) throws PersonNotFoundException, InternalServerErrorException, BadRequestException;

	void updatePerson(Person person) throws PersonNotFoundException, BadRequestException, InternalServerErrorException;

	ResponseEntity<String> findOrCreate(Person p) throws PersonNotFoundException, BadRequestException, InternalServerErrorException;

	void deletePersonById(String id) throws PersonNotFoundException, InternalServerErrorException, BadRequestException;
}
