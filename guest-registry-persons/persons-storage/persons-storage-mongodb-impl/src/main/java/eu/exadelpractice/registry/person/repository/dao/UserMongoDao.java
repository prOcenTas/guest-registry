package eu.exadelpractice.registry.person.repository.dao;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import eu.exadelpractice.registry.person.entity.UserEntity;
import eu.exadelpractice.registry.person.mapper.EntityMapper;
import eu.exadelpractice.registry.person.model.User;
import eu.exadelpractice.registry.person.repository.UserRepository;
import eu.exadelpractice.registry.person.repository.UserRepositoryMongo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMongoDao implements UserRepository {
	private final UserRepositoryMongo userRepositoryMongo;
	private final EntityMapper mapper;
	
	@Override
	public List<User> findAll() {
		List<UserEntity> entityList = userRepositoryMongo.findAll();
		List<User> userList = mapper.mapLists(entityList, User.class);
		return userList;
	}

	@Override
	public Optional<User> findById(String id) {
		Optional<UserEntity> optionalEntity = userRepositoryMongo.findById(id);
		Optional<User> optionalUser = Optional.ofNullable(null);
		if (optionalEntity.isPresent()) {
			optionalUser = Optional.of(mapper.map(optionalEntity.get(), User.class));
		}
		return optionalUser;
	}

	@Override
	public void save(User user) {
		UserEntity entity = mapper.map(user, UserEntity.class);
		if (entity.getId() == null) {
			entity.setId(new ObjectId().toString());
		}
		entity = userRepositoryMongo.save(entity);
		user.setId(entity.getId());
		
	}

	@Override
	public void deleteById(String id) {
		userRepositoryMongo.deleteById(id);
	}

	@Override
	public User findByPerson_id(String id) {
		UserEntity entity = userRepositoryMongo.findByPerson_id(id);
		if(entity == null) {
			return null;
		}
		User user = mapper.map(entity, User.class);
		return user;
	}

}
