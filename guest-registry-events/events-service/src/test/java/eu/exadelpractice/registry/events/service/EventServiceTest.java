package eu.exadelpractice.registry.events.service;

import static org.junit.jupiter.api.Assertions.*;

import eu.exadelpractice.registry.events.model.Event;
import eu.exadelpractice.registry.events.model.LocationRef;
import eu.exadelpractice.registry.events.model.WorkerRef;
import eu.exadelpractice.registry.events.repository.EventRepository;
import eu.exadelpractice.registry.events.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventServiceImpl service;

    private LocationRef location = new LocationRef("3543", "location title");
    private WorkerRef person = new WorkerRef("12345", "Vardenis", "Pavardenis");
    private Event mockEvent = new Event("1", "Name", "Description", location, new ArrayList<>(Arrays.asList(person)));

    @Test
    public void findById() throws Exception{
        Optional<Event> optionalEvent=Optional.of(mockEvent);
        Mockito.when(repository.findById("1")).thenReturn(optionalEvent);
        assertEquals(mockEvent, service.getEvent("1"));
    }

}