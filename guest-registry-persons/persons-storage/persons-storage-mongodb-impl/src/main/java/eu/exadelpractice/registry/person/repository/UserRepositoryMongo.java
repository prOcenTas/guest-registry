package eu.exadelpractice.registry.person.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import eu.exadelpractice.registry.person.entity.UserEntity;

public interface UserRepositoryMongo extends MongoRepository<UserEntity, String> {

	public UserEntity findByPerson_id(String id);
}
