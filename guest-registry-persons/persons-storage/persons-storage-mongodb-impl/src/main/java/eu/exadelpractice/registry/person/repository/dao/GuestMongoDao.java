package eu.exadelpractice.registry.person.repository.dao;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import eu.exadelpractice.registry.person.entity.GuestEntity;
import eu.exadelpractice.registry.person.mapper.EntityMapper;
import eu.exadelpractice.registry.person.model.Guest;
import eu.exadelpractice.registry.person.repository.GuestRepository;
import eu.exadelpractice.registry.person.repository.GuestRepositoryMongo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestMongoDao implements GuestRepository {
	private final GuestRepositoryMongo guestRepositoryMongo;
	private final EntityMapper mapper;
	@Override
	public List<Guest> findAll() {
		List<GuestEntity> entityList = guestRepositoryMongo.findAll();
		List<Guest> guestList = mapper.mapLists(entityList, Guest.class);
		return guestList;
	}

	@Override
	public Optional<Guest> findById(String id) {
		Optional<GuestEntity> optionalEntity = guestRepositoryMongo.findById(id);
		Optional<Guest> optionalGuest = Optional.ofNullable(null);
		if(optionalEntity.isPresent()) {
			optionalGuest = Optional.of(mapper.map(optionalEntity.get(), Guest.class));
		}
		return optionalGuest;
	}

	@Override
	public void save(Guest guest) {
		GuestEntity entity = mapper.map(guest, GuestEntity.class);
		if(entity.getId() == null) {
			entity.setId(new ObjectId().toString());
		}
		entity = guestRepositoryMongo.save(entity);
		guest.setId(entity.getId());
	}

	@Override
	public void deleteById(String id) {
		guestRepositoryMongo.deleteById(id);
		
	}

}
