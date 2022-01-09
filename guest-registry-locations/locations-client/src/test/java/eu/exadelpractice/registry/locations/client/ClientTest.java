package eu.exadelpractice.registry.locations.client;

import eu.exadelpractice.registry.locations.exception.LocationNotFoundException;
import eu.exadelpractice.registry.locations.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@SpringBootApplication
@TestPropertySource(locations = "classpath:testing.properties")
@ComponentScan(basePackages = {"eu.exadelpractice.registry.locations.client"})
public class ClientTest {
    @Autowired
    LocationClient client;

    @Test
    void clientTest()throws LocationNotFoundException {
        Location location=client.getLocationById("1");
        System.out.println(location);
    }
}
