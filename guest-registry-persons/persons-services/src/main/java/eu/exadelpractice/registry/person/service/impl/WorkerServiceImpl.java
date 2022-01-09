package eu.exadelpractice.registry.person.service.impl;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.repository.WorkerRepository;
import eu.exadelpractice.registry.person.service.WorkerService;
import eu.exadelpractice.registry.person.validator.WorkerValidator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

	private final WorkerRepository workerRepository;
	private final WorkerValidator workerValidator;

	@Override
	public List<Worker> findAll() {
		return workerRepository.findAll();
	}

	@Override
	public String createWorker(Worker worker) throws ObjectNotFoundException {
		worker.setId(null);
		workerValidator.validateNewWorker(worker);
		workerRepository.save(worker);
		return worker.getId();
	}

	@Override
	public Worker getWorkerById(String id) throws WorkerNotFoundException {
		return workerRepository.findById(id)
				.orElseThrow(() -> new WorkerNotFoundException("Worker does not exist with an id " + id));
	}

	@Override
	public void updateWorker(Worker worker) throws ObjectNotFoundException {
		workerValidator.id(worker);
		workerValidator.validateNewWorker(worker);
		this.getWorkerById(worker.getId());
		workerRepository.save(worker);
	}

	@Override
	public void deleteWorkerById(String id) throws WorkerNotFoundException {
		this.getWorkerById(id);
		workerRepository.deleteById(id);
	}

}
