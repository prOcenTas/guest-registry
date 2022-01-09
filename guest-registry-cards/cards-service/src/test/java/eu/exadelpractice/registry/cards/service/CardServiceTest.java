package eu.exadelpractice.registry.cards.service;

import static org.junit.jupiter.api.Assertions.*;

import eu.exadelpractice.registry.cards.repository.CardRepository;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.cards.model.LocationRef;
import eu.exadelpractice.registry.cards.model.PersonRef;
import eu.exadelpractice.registry.cards.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    private CardRepository repository;

    @InjectMocks
    private CardServiceImpl service;

    private LocationRef location = new LocationRef("2563", "Vilniaus office");
    private PersonRef person = new PersonRef("293152", "Vardenis", "Pavardenis");
    private Card mockCard = new Card("1", LocalDate.of(2012, 01, 01),
            LocalDate.of(2025, 01, 01), "Java Developer", location, person);

    private LocationRef location2 = new LocationRef("6540", "Klaipeda office");
    private PersonRef person2 = new PersonRef("135982", "Vardenis2", "Pavardenis2");
    private Card mockCard2 = new Card("2", LocalDate.of(2013, 01, 01),
            LocalDate.of(2026, 01, 01), "DevOp", location2, person2);

    @Test
    public void findById() throws Exception{
        Optional<Card> optionalCard=Optional.of(mockCard);
        Mockito.when(repository.findById(mockCard.getId())).thenReturn(optionalCard);
        assertEquals(service.getCard(mockCard.getId()), mockCard);
    }

    @Test
    public void findAll() throws Exception {

        List<Card> cardList = new ArrayList<Card>();
        cardList.add(mockCard);
        cardList.add(mockCard2);
        // Optional<List> optionalList = Optional.of(personList);
        //Mockito.when(repository.findAll()).thenReturn(cardList);
        //assertEquals(service.getAll(), cardList);
    }
}