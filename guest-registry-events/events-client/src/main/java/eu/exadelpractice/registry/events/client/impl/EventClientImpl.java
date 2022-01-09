package eu.exadelpractice.registry.events.client.impl;

import eu.exadelpractice.registry.common.client.GenericClientImpl;
import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.events.client.EventClient;
import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class EventClientImpl extends GenericClientImpl<Event, EventNotFoundException> implements EventClient {

    public EventClientImpl() {
        super(Event.class, "events/");
    }

    private final Map<String, Event> cach = new HashMap<>();

    @Override
    public Event getEventById(String id) throws EventNotFoundException, InternalServerErrorException, BadRequestException {
        return getFromCash(id);
    }

    private Event getFromCash(String id) throws EventNotFoundException, InternalServerErrorException, BadRequestException {
        Event cached = cach.get(id);
        if (cached == null) {
            cached = getObjectById(id);
            cach.put(id, cached);
        }

        return cached;
    }

    @Override
    public List<Event> getAllEvents() throws EventNotFoundException, InternalServerErrorException, BadRequestException {
        return this.getAllObjects(Event[].class);
    }

    @Override
    public String saveEvent(Event event) throws EventNotFoundException, InternalServerErrorException, BadRequestException {
        return this.createObject(event);
    }

    @Override
    public void updateEvent(Event event) throws EventNotFoundException, BadRequestException, InternalServerErrorException {
        this.updateObject(event);
    }

    @Override
    public void deleteEvent(String id) throws EventNotFoundException, InternalServerErrorException, BadRequestException {
        this.deleteObjectById(id);
    }

	@Override
	protected EventNotFoundException createDomainNotFoundException(HttpClientErrorException ex) {
		return new EventNotFoundException(ex.getMessage());
	}
}
