package eu.exadelpractice.registry.cards.client;

import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.model.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@SpringBootApplication
@TestPropertySource(locations = "classpath:testing.properties")
@ComponentScan(basePackages = {"eu.exadelpractice.registry.cards.client"})
public class ClientTest {
    @Autowired
    private CardClient client;

//    @Test
//    void clientTest() throws CardNotFoundException {
//        Card card=client.getCardById("1");
//        System.out.println(card);
//    }
}
