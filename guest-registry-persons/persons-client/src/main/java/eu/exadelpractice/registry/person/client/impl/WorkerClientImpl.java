package eu.exadelpractice.registry.person.client.impl;

import eu.exadelpractice.registry.common.client.GenericClientImpl;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import eu.exadelpractice.registry.person.client.WorkerClient;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Worker;

@Service
//@RequiredArgsConstructor
public class WorkerClientImpl extends GenericClientImpl<Worker, WorkerNotFoundException> implements WorkerClient {

	public WorkerClientImpl() {
		super(Worker.class, "workers/");
	}

//	@PostConstruct
//    private void postConstruct() {
//        genericImpl.setType(Worker.class);
//        genericImpl.setExceptionType(WorkerNotFoundException.class);
//    }
	
	@Override
	public Worker getWorkerById(String id) throws WorkerNotFoundException, InternalServerErrorException, BadRequestException {
		return this.getObjectById(id);
	}

	@Override
	public List<Worker> getAllWorkers() throws WorkerNotFoundException, InternalServerErrorException, BadRequestException  {
		return this.getAllObjects(Worker[].class);
	}

	@Override
	public String createWorker(Worker worker) throws WorkerNotFoundException, InternalServerErrorException, BadRequestException {
		return this.createObject(worker);
	}

	@Override
	public void updateWorker(Worker worker) throws WorkerNotFoundException, BadRequestException, InternalServerErrorException {
		this.updateObject(worker);
	}

	@Override
	public void deleteWorkerById(String id) throws WorkerNotFoundException, InternalServerErrorException, BadRequestException {
		this.deleteObjectById(id);
	}

	@Override
	protected WorkerNotFoundException createDomainNotFoundException(HttpClientErrorException ex) {
		return new WorkerNotFoundException(ex.getMessage());
	}
}
