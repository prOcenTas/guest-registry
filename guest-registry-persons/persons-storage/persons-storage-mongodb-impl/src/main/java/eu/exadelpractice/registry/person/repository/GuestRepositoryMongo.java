package eu.exadelpractice.registry.person.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import eu.exadelpractice.registry.person.entity.GuestEntity;

public interface GuestRepositoryMongo extends MongoRepository<GuestEntity, String> {

}
