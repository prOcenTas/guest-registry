package eu.exadelpractice.registry.events.repository.dao;

import eu.exadelpractice.registry.events.entity.EventEntity;
import eu.exadelpractice.registry.events.mapper.Mapper;
import eu.exadelpractice.registry.events.model.Event;
import eu.exadelpractice.registry.events.repository.EventRepository;
import eu.exadelpractice.registry.events.repository.EventRepositoryMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventMongoDao implements EventRepository {
    private final Mapper mapper;
    private final EventRepositoryMongo repository;

    @Override
    public List<Event> findAll() {
        List<EventEntity> eventEntityList=repository.findAll();
        List<Event> eventList=mapper.mapList(eventEntityList,Event.class);
        return eventList;
    }

    @Override
    public Optional<Event> findById(String id) {
        Optional<Event> optionalEvent=Optional.ofNullable(null);
        Optional<EventEntity> optionalEventEntity=repository.findById(id);
        if(optionalEventEntity.isPresent()){
            optionalEvent=Optional.of(mapper.map(optionalEventEntity.get(),Event.class));
        }
        return optionalEvent;
    }

    @Override
    public void save(Event event) {
        EventEntity eventEntity=mapper.map(event,EventEntity.class);
        eventEntity=repository.save(eventEntity);
        event=mapper.map(eventEntity,Event.class);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
