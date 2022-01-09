package eu.exadelpractice.registry.person.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import eu.exadelpractice.registry.person.entity.PersonEntity;


public interface PersonRepositoryMongo extends MongoRepository<PersonEntity, String> {

	@Query("{name: ?0, surname: ?1, dateOfBirth: ?2}")
	public PersonEntity findByNameSurnameDate(String name, String surname, LocalDate dateOfBirth);
}
