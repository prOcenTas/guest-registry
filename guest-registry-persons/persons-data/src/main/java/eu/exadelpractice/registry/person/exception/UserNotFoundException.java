package eu.exadelpractice.registry.person.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class UserNotFoundException extends ObjectNotFoundException {

	public UserNotFoundException(String message) {
		super(message);
	}
}
