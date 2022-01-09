package eu.exadelpractice.registry.events.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class EventNotFoundException extends ObjectNotFoundException {
	public EventNotFoundException(String message) {
		super(message);
	}
}
