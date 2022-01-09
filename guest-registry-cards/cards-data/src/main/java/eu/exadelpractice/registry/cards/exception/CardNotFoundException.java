package eu.exadelpractice.registry.cards.exception;


import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;

public class CardNotFoundException extends ObjectNotFoundException {
	public CardNotFoundException(String message) {
		super(message);
	}
}
