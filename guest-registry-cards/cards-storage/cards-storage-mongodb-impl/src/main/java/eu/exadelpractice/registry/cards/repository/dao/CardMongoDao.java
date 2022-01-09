package eu.exadelpractice.registry.cards.repository.dao;

import eu.exadelpractice.registry.cards.repository.CardRepository;
import eu.exadelpractice.registry.cards.entity.CardEntity;
import eu.exadelpractice.registry.cards.mapper.Mapper;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.cards.repository.CardRepositoryMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CardMongoDao implements CardRepository {
    private final Mapper mapper;
    private final CardRepositoryMongo repository;


    @Override
    public List<Card> findAll() {
        List<CardEntity> cardEntityList=repository.findAll();
        List<Card> cardList=mapper.mapList(cardEntityList,Card.class);
        return cardList;
    }

    @Override
    public Optional<Card> findById(String id) {
        Optional<Card> optionalCard=Optional.ofNullable(null);
        Optional<CardEntity> optionalCardEntity=repository.findById(id);
        if(optionalCardEntity.isPresent()){
            optionalCard=Optional.of(mapper.map(optionalCardEntity.get(),Card.class));
        }
        return optionalCard;
    }

    @Override
    public void save(Card card) {
        CardEntity cardEntity=mapper.map(card,CardEntity.class);
        cardEntity=repository.save(cardEntity);
        card=mapper.map(cardEntity,Card.class);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
