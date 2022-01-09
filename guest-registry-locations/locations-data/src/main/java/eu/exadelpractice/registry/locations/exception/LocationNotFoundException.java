package eu.exadelpractice.registry.locations.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class LocationNotFoundException extends ObjectNotFoundException {
	public LocationNotFoundException(String message) {
		super(message);
	}
}
