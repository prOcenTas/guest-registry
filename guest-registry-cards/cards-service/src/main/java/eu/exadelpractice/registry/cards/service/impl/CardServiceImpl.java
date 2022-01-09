package eu.exadelpractice.registry.cards.service.impl;

import eu.exadelpractice.registry.cards.exception.CardFieldBadValueException;
import eu.exadelpractice.registry.cards.exception.LocationFieldBadValueException;
import eu.exadelpractice.registry.cards.repository.CardRepository;
import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.cards.service.CardService;
import eu.exadelpractice.registry.cards.validator.CardValidator;
import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository repository;
    private final CardValidator validator;

    @Override
    public void saveCard(Card card) throws ObjectNotFoundException {
        if (card.getId() == null) {
            card.setId(new ObjectId().toString());
        }
        repository.save(card);
    }

    @Override
    public Card getCard(String id) throws CardNotFoundException {
        return repository.findById(id).orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
    }

    @Override
    public List<Card> getAll(Map<String,String[]> parameters) {
        List<Card> cards=repository.findAll();
        return repository.findAll();
    }

    @Override
    public void updateCard(Card card) throws CardNotFoundException, CardFieldBadValueException, LocationFieldBadValueException {
        validator.id(card);
        this.getCard(card.getId());
        repository.save(card);
    }

    @Override
    public void deleteCard(String id) throws CardNotFoundException {
        if (this.getCard(id) == null) {
            throw new CardNotFoundException("Id was not provided or found");
        }
        repository.deleteById(id);
    }
}
