package eu.exadelpractice.registry.person.client;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Worker;


public interface WorkerClient {
	Worker getWorkerById(String id) throws WorkerNotFoundException, InternalServerErrorException, BadRequestException;

	List<Worker> getAllWorkers() throws WorkerNotFoundException, InternalServerErrorException, BadRequestException;

	String createWorker(Worker worker) throws WorkerNotFoundException, InternalServerErrorException, BadRequestException;

	void updateWorker(Worker worker) throws WorkerNotFoundException, BadRequestException, InternalServerErrorException;

	void deleteWorkerById(String id) throws WorkerNotFoundException, InternalServerErrorException, BadRequestException;
}
