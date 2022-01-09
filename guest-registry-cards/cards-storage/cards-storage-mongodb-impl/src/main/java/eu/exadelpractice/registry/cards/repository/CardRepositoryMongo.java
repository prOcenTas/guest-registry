package eu.exadelpractice.registry.cards.repository;

import eu.exadelpractice.registry.cards.entity.CardEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRepositoryMongo extends MongoRepository<CardEntity, String> {
}
