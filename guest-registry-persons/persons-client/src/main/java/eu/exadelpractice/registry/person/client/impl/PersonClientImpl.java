package eu.exadelpractice.registry.person.client.impl;

import eu.exadelpractice.registry.common.client.GenericClientImpl;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.person.client.PersonClient;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.Person;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
//@RequiredArgsConstructor
public class PersonClientImpl extends GenericClientImpl<Person, PersonNotFoundException> implements PersonClient {

	public PersonClientImpl() {
		super(Person.class, "persons/");
	}

//	@PostConstruct
//    private void postConstruct() {
//        genericImpl.setType(Person.class);
//        genericImpl.setExceptionType(PersonNotFoundException.class);
//    }
	
	@Override
	public Person getPersonById(String id) throws PersonNotFoundException, InternalServerErrorException, BadRequestException {
		return this.getObjectById(id);
	}

	@Override
	public List<Person> getAllPersons() throws PersonNotFoundException, InternalServerErrorException, BadRequestException {
		return this.getAllObjects(Person[].class);
	}

	@Override
	public String createPerson(Person person) throws PersonNotFoundException, InternalServerErrorException, BadRequestException {
		return this.createObject(person);
	}

	@Override
	public void updatePerson(Person person) throws PersonNotFoundException, BadRequestException, InternalServerErrorException {
		this.updateObject(person);
	}

	@Override
	public ResponseEntity<String> findOrCreate(Person p) throws PersonNotFoundException, BadRequestException, InternalServerErrorException {
		return this.findOrCreateObject(p);
	}

	@Override
	public void deletePersonById(String id) throws PersonNotFoundException, InternalServerErrorException, BadRequestException {
		this.deleteObjectById(id);
	}

	@Override
	protected PersonNotFoundException createDomainNotFoundException(HttpClientErrorException ex) {
		return new PersonNotFoundException(ex.getMessage());
	}
}
