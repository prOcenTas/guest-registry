package eu.exadelpractice.registry.person.exception;


import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class PersonNotFoundException extends ObjectNotFoundException {

	public PersonNotFoundException(String message) {
		super(message);
	}

}
