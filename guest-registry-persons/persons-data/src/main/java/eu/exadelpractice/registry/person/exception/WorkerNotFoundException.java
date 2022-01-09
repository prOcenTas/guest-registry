package eu.exadelpractice.registry.person.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class WorkerNotFoundException extends ObjectNotFoundException {

	public WorkerNotFoundException(String message) {
		super(message);
	}
}
