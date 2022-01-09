package eu.exadelpractice.registry.events.repository;

import eu.exadelpractice.registry.events.model.Event;
import eu.exadelpractice.registry.events.model.LocationRef;
import eu.exadelpractice.registry.events.model.WorkerRef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
//@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan(basePackages = {"eu.exadelpractice.registry.events"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EventRepositoryTest {

    @Autowired
    private EventRepository repository;

    private LocationRef location = new LocationRef("3543", "location title");
    private WorkerRef person = new WorkerRef("12345", "Vardenis", "Pavardenis");
    private Event mockEvent = new Event("1", "Name", "Description", location, new ArrayList<>(Arrays.asList(person)));

    @Test
    public void findById() throws Exception {
        repository.save(mockEvent);
        Optional<Event> optionalEvent = repository.findById("1");
        assertTrue(optionalEvent.isPresent());
        Event event = optionalEvent.orElseThrow();

        assertEquals(mockEvent.getId(), event.getId());
        assertEquals(mockEvent.getName(), event.getName());
        assertEquals(mockEvent.getDescription(), event.getDescription());
        assertEquals(mockEvent.getLocationRef(), event.getLocationRef());
        assertEquals(mockEvent.getPersonRef(), event.getPersonRef());
    }

    @Test
    public void deleteById() throws Exception{
        repository.save(mockEvent);
        Optional<Event> optionalEvent = repository.findById("1");
        assertTrue(optionalEvent.isPresent());

        repository.deleteById("1");
        optionalEvent=repository.findById("1");
        assertTrue(optionalEvent.isEmpty());
    }

}