package eu.exadelpractice.registry.locations.repository;

import eu.exadelpractice.registry.locations.entity.LocationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LocationRepositoryMongo extends MongoRepository<LocationEntity, String> {
	// TODO create a converter for ENUM for it to be able to filter
	@Query("{'locationType':?0}")
	List<LocationEntity> findByType(String locationType);

	@Query("{'Address.city':?0}")
	List<LocationEntity> findByCity(String city);

	@Query("{'Address.country':?0}")
	List<LocationEntity> findByCountry(String country);
}
