package eu.exadelpractice.registry.person.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.person.exception.PersonNotFoundException;
import eu.exadelpractice.registry.person.model.OperationResult;
import eu.exadelpractice.registry.person.model.Person;
import eu.exadelpractice.registry.person.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("persons")
public class PersonController {

	private final PersonService personService;

	@GetMapping
	public List<Person> getAllPersons(HttpServletRequest httpServletRequest) throws BadRequestException {
		Map<String, String[]> map = httpServletRequest.getParameterMap();
		/*for(String key : map.keySet()) {
			
			System.out.println("key: " + key + " value: " + map.get(key));
		}*/
		return personService.findAll(map);
	}

	@GetMapping("{id}")
	public Person getPersonById(@PathVariable String id) throws PersonNotFoundException {
		return personService.getPersonById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public String createPerson(@RequestBody Person person) throws PersonNotFoundException {
		return personService.createPerson(person);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping
	public void updatePerson(@RequestBody Person person) throws PersonNotFoundException {
		personService.updatePerson(person);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deletePersonById(@PathVariable String id) throws PersonNotFoundException {
		personService.deletePersonById(id);
	}

	@PostMapping("find/create")
	public ResponseEntity<String> findOrCreate(@RequestBody Person p) throws PersonNotFoundException {
		OperationResult o = personService.findOrCreate(p);
		if (o.isCreated()) {
			return new ResponseEntity<String>(o.getId(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(o.getId(), HttpStatus.OK);
		}
	}
}
