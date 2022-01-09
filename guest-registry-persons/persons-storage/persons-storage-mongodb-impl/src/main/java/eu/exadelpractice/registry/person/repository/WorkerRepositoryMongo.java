package eu.exadelpractice.registry.person.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import eu.exadelpractice.registry.person.entity.WorkerEntity;


public interface WorkerRepositoryMongo extends MongoRepository<WorkerEntity, String> {

	public WorkerEntity findByPerson_id(String id);
}
