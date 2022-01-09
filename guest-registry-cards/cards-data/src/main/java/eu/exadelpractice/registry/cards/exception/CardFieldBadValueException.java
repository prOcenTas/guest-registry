package eu.exadelpractice.registry.cards.exception;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class CardFieldBadValueException extends ObjectNotFoundException {
    public CardFieldBadValueException(String message) {
        super(message);
    }
}
