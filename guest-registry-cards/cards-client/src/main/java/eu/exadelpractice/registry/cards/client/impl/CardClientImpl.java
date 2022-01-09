package eu.exadelpractice.registry.cards.client.impl;

import eu.exadelpractice.registry.cards.client.CardClient;
import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.common.client.GenericClientImpl;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class CardClientImpl extends GenericClientImpl<Card, CardNotFoundException> implements CardClient {

    public CardClientImpl() {
        super(Card.class, "cards/");
    }

    private final Map<String, Card> cach = new HashMap<>();

    @Override
    public Card getCardById(String id) throws CardNotFoundException, BadRequestException, InternalServerErrorException {
        return getFromCash(id);
    }

    private Card getFromCash(String id) throws CardNotFoundException, InternalServerErrorException, BadRequestException {
        Card cached = cach.get(id);
        if (cached == null) {
            cached = getObjectById(id);
            cach.put(id, cached);
        }

        return cached;
    }

    @Override
    public List<Card> getAllCards() throws CardNotFoundException, InternalServerErrorException, BadRequestException {
        return this.getAllObjects(Card[].class);
    }

    @Override
    public String saveCard(Card card) throws CardNotFoundException, BadRequestException, InternalServerErrorException {
        return this.createObject(card);
    }

    @Override
    public void updateCard(Card card) throws CardNotFoundException, InternalServerErrorException, BadRequestException {
        this.updateObject(card);
    }

    @Override
    public void deleteCard(String id) throws CardNotFoundException, InternalServerErrorException, BadRequestException {
        this.deleteObjectById(id);
    }

    @Override
    protected CardNotFoundException createDomainNotFoundException(HttpClientErrorException ex) {

        return new CardNotFoundException(ex.getMessage());
    }
}
