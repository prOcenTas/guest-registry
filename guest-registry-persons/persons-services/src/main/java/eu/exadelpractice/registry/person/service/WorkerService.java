package eu.exadelpractice.registry.person.service;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Worker;

import java.util.List;

public interface WorkerService {

	public List<Worker> findAll();

	public String createWorker(Worker worker) throws ObjectNotFoundException;

	public Worker getWorkerById(String id) throws WorkerNotFoundException;

	public void updateWorker(Worker worker) throws ObjectNotFoundException;

	public void deleteWorkerById(String id) throws WorkerNotFoundException;

}
