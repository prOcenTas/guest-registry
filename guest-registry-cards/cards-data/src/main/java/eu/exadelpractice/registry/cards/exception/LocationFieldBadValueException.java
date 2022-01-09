package eu.exadelpractice.registry.cards.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class LocationFieldBadValueException extends ObjectNotFoundException {
    public LocationFieldBadValueException(String message) {
        super(message);
    }
}
