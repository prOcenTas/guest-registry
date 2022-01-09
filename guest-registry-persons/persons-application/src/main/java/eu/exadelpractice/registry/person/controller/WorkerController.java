package eu.exadelpractice.registry.person.controller;

import java.util.List;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.person.exception.WorkerNotFoundException;
import eu.exadelpractice.registry.person.model.Worker;
import eu.exadelpractice.registry.person.service.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("workers")
public class WorkerController {

	private final WorkerService workerService;

	@GetMapping
	public List<Worker> findAllWorkers() {
		return workerService.findAll();
	}

	@GetMapping("{id}")
	public Worker getWorkerById(@PathVariable String id) throws WorkerNotFoundException {
		return workerService.getWorkerById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public String createWorker(@RequestBody Worker worker) throws ObjectNotFoundException {
		return workerService.createWorker(worker);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping
	public void updateWorker(@RequestBody Worker worker) throws ObjectNotFoundException {
		workerService.updateWorker(worker);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deleteWorkerById(@PathVariable String id) throws WorkerNotFoundException {
		workerService.deleteWorkerById(id);
	}
}
