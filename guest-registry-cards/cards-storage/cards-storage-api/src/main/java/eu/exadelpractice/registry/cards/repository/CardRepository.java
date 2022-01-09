package eu.exadelpractice.registry.cards.repository;

import eu.exadelpractice.registry.cards.model.Card;


import java.util.List;
import java.util.Optional;

public interface CardRepository {
    public List<Card> findAll();

    public Optional<Card> findById(String id);

    public void save(Card card);

    public void deleteById(String id);
}
