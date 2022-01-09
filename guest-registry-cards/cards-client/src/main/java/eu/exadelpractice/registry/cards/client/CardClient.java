package eu.exadelpractice.registry.cards.client;

import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;

import java.util.List;

public interface CardClient {
    Card getCardById(String id) throws CardNotFoundException, BadRequestException, InternalServerErrorException;

    List<Card> getAllCards() throws CardNotFoundException, InternalServerErrorException, BadRequestException;

    String saveCard(Card card) throws CardNotFoundException, BadRequestException, InternalServerErrorException;

    void updateCard(Card card) throws CardNotFoundException, InternalServerErrorException, BadRequestException;

    void deleteCard(String id) throws CardNotFoundException, InternalServerErrorException, BadRequestException;
}
