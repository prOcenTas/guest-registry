package eu.exadelpractice.registry.events.repository;

import java.util.List;
import java.util.Optional;
import eu.exadelpractice.registry.events.model.Event;

public interface EventRepository {
    public List<Event> findAll();

    public Optional<Event> findById(String id);

    public void save(Event event);

    public void deleteById(String id);
}
