package eu.exadelpractice.registry.events.repository;

import eu.exadelpractice.registry.events.entity.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepositoryMongo extends MongoRepository<EventEntity, String> {
}
