package eu.exadelpractice.registry.person.repository.dao;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import eu.exadelpractice.registry.person.entity.WorkerEntity;
import eu.exadelpractice.registry.person.mapper.EntityMapper;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.repository.WorkerRepositoryMongo;
import eu.exadelpractice.registry.person.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerMongoDao implements WorkerRepository {
	private final WorkerRepositoryMongo workerRepositoryMongo;
	private final EntityMapper mapper;

	@Override
	public List<Worker> findAll() {
		List<WorkerEntity> entityList = workerRepositoryMongo.findAll();
		List<Worker> workerList = mapper.mapLists(entityList, Worker.class);
		return workerList;
	}

	@Override
	public Optional<Worker> findById(String id) {
		Optional<WorkerEntity> optionalEntity = workerRepositoryMongo.findById(id);
		Optional<Worker> optionalWorker = Optional.ofNullable(null);
		if (optionalEntity.isPresent()) {
			optionalWorker = Optional.of(mapper.map(optionalEntity.get(), Worker.class));
		}
		return optionalWorker;
	}

	@Override
	public void save(Worker worker) {
		WorkerEntity entity = mapper.map(worker, WorkerEntity.class);
		if (entity.getId() == null) {
			entity.setId(new ObjectId().toString());
		}
		entity = workerRepositoryMongo.save(entity);
		worker.setId(entity.getId());

	}

	@Override
	public void deleteById(String id) {
		workerRepositoryMongo.deleteById(id);

	}

	@Override
	public Worker findByPerson_id(String id) {
		WorkerEntity entity = workerRepositoryMongo.findByPerson_id(id);
		if(entity == null) {
			return null;
		}
		Worker worker = mapper.map(entity, Worker.class);
		return worker;
	}

}
