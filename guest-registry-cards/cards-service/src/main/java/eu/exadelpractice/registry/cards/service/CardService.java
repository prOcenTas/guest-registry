package eu.exadelpractice.registry.cards.service;


import eu.exadelpractice.registry.cards.exception.CardFieldBadValueException;
import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.exception.LocationFieldBadValueException;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;


import java.util.List;
import java.util.Map;

public interface CardService {
	public void saveCard(Card card) throws ObjectNotFoundException;

	public Card getCard(String id) throws CardNotFoundException;

	public List<Card> getAll(Map<String,String[]> parameters);

	public void updateCard(Card card) throws CardNotFoundException, CardFieldBadValueException, LocationFieldBadValueException;

	public void deleteCard(String id) throws CardNotFoundException;
}
