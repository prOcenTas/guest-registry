package eu.exadelpractice.registry.events.service;

import eu.exadelpractice.registry.common.model.exception.ObjectNotFoundException;
import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;


import java.util.List;

public interface EventService {
	public void saveEvent(Event event) throws ObjectNotFoundException;

	public Event getEvent(String id) throws EventNotFoundException;

	public List<Event> getAll();

	public void updateEvent(Event event) throws EventNotFoundException;

	public void deleteEvent(String id) throws EventNotFoundException;
}
