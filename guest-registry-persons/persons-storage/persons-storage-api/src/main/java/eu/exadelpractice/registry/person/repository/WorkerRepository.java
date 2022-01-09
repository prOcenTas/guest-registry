package eu.exadelpractice.registry.person.repository;

import eu.exadelpractice.registry.person.model.Worker;

import java.util.List;
import java.util.Optional;


public interface WorkerRepository {

	public List<Worker> findAll();

	public Optional<Worker> findById(String id);

	public void save(Worker worker);

	public void deleteById(String id);
	
	public Worker findByPerson_id(String id);
}
