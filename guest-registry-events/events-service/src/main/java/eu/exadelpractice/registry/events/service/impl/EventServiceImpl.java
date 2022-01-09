package eu.exadelpractice.registry.events.service.impl;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;
import eu.exadelpractice.registry.events.repository.EventRepository;
import eu.exadelpractice.registry.events.service.EventService;
import eu.exadelpractice.registry.events.validator.EventValidator;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final EventRepository repository;
	private final EventValidator validator;

	@Override
	public void saveEvent(Event event) throws ObjectNotFoundException {
		if(event.getId() == null){
			event.setId(new ObjectId().toString());
		}
		repository.save(event);
	}

	@Override
	public Event getEvent(String id) throws EventNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
	}

	@Override
	public List<Event> getAll() {
		return repository.findAll();
	}

	@Override
	public void updateEvent(Event event) throws EventNotFoundException {
		validator.id(event);
		this.getEvent(event.getId());
		repository.save(event);
	}

	@Override
	public void deleteEvent(String id) throws EventNotFoundException {
		if (this.getEvent(id) == null) {
			throw new EventNotFoundException("Id was not provided or found");
		}
		repository.deleteById(id);
	}
}
