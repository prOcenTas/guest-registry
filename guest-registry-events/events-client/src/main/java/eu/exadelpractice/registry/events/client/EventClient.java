package eu.exadelpractice.registry.events.client;

import eu.exadelpractice.registry.common.model.exception.BadRequestException;
import eu.exadelpractice.registry.common.model.exception.InternalServerErrorException;
import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;

import java.util.List;

public interface EventClient {
    public Event getEventById(String id) throws EventNotFoundException, InternalServerErrorException, BadRequestException;

    List<Event> getAllEvents() throws EventNotFoundException, InternalServerErrorException, BadRequestException;

    String saveEvent(Event event) throws EventNotFoundException, InternalServerErrorException, BadRequestException;

    void updateEvent(Event event) throws EventNotFoundException, BadRequestException, InternalServerErrorException;

    void deleteEvent(String id) throws EventNotFoundException, InternalServerErrorException, BadRequestException;
}
