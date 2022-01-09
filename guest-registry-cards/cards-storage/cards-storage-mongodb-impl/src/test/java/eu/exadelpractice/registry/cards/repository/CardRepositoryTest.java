package eu.exadelpractice.registry.cards.repository;

import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.cards.model.LocationRef;
import eu.exadelpractice.registry.cards.model.PersonRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MainTestClass.class)
@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan(basePackages = {"eu.exadelpractice.registry.cards"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CardRepositoryTest {

    @Autowired
    private CardRepository repository;

    private LocationRef location = new LocationRef("2563", "Vilniaus office");
    private PersonRef person = new PersonRef("293152", "Vardenis", "Pavardenis");
    private Card mockCard = new Card("1", LocalDate.of(2012, 01, 01),
            LocalDate.of(2025, 01, 01), "Java Developer", location, person);

    //private LocationRef location2 = new LocationRef("4632", "Klaipedos office");
   // private PersonRef person2 = new PersonRef("159648", "Vardenis2", "Pavardenis2");
    //private Card mockCard2 = new Card("2", LocalDate.of(2010, 01, 01),
        //    LocalDate.of(2023, 01, 01), "Python Dev", location2, person2);

    @Test
    public void findById() throws Exception {
        repository.save(mockCard);
        Optional<Card> optionalCard = repository.findById("1");
        assertTrue(optionalCard.isPresent());
        Card card = optionalCard.orElseThrow();

//        assertTrue(card.equals(mockCard));
        assertEquals(mockCard.getId(), card.getId());
        assertEquals(mockCard.getValidFrom(), card.getValidFrom());
        assertEquals(mockCard.getValidTo(), card.getValidTo());
        assertEquals(mockCard.getCardTitle(), card.getCardTitle());
        assertEquals(mockCard.getLocationRef(), card.getLocationRef());
        assertEquals(mockCard.getPersonRef(), card.getPersonRef());

        Optional<Card> optionalNullCard=repository.findById("1234");
        assertTrue(optionalNullCard.isEmpty());
    }

    @Test
    public void findByIdNull() throws Exception{
        String id="6";
        Optional<Card> optionalCard=repository.findById(id);
        assertTrue(optionalCard.isEmpty());
    }

    @Test
    public void deleteById() throws Exception{
        repository.save(mockCard);
        Optional<Card> optionalCard=repository.findById("1");
        assertTrue(optionalCard.isPresent());

        repository.deleteById("1");
        optionalCard=repository.findById("1");
        assertTrue(optionalCard.isEmpty());
    }
}