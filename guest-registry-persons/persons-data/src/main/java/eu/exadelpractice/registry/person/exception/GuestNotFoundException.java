package eu.exadelpractice.registry.person.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class GuestNotFoundException extends ObjectNotFoundException {

	public GuestNotFoundException(String message) {
		super(message);
	}
}
