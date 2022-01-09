package eu.exadelpractice.registry.events.client;

import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@SpringBootApplication
@TestPropertySource(locations = "classpath:testing.properties")
@ComponentScan(basePackages = {"eu.exadelpractice.registry.events.client"})
public class ClientTest {
    @Autowired
    private EventClient client;

//    @Test
//    void clientTest() throws EventNotFoundException {
//        Event event=client.getEventById("1");
//        System.out.println(event);
//    }
}
