package eu.exadelpractice.registry.cards.controller.controller;

import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import eu.exadelpractice.registry.cards.controller.CardController;
import eu.exadelpractice.registry.cards.controller.MainTestClass;
import eu.exadelpractice.registry.cards.exception.CardNotFoundException;
import eu.exadelpractice.registry.cards.model.Card;
import eu.exadelpractice.registry.cards.model.LocationRef;
import eu.exadelpractice.registry.cards.model.PersonRef;
import eu.exadelpractice.registry.cards.service.impl.CardServiceImpl;
import eu.exadelpractice.registry.common.model.ErrorHelper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
@WebMvcTest(CardController.class)
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = MainTestClass.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardServiceImpl service;

    @MockBean
    private ErrorHelper he;

    private LocationRef location=new LocationRef("2563","Vilniaus office");
    private PersonRef person=new PersonRef("293152","Vardenis","Pavardenis");
    private Card mockCard=new Card("1", LocalDate.of(2012,01,01),LocalDate.of(2025,01,01),"Java Developer",location,person);


    @Test
    public void getCardById() throws Exception{
        Mockito.when(service.getCard("1")).thenReturn(mockCard);
        mockMvc.perform(MockMvcRequestBuilders.get("/cards/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validFrom",is("01-01-2012")))
                .andExpect(jsonPath("$.validTo",is("01-01-2025")))
                .andExpect(jsonPath("$.cardTitle",is("Java Developer")))
                .andExpect(jsonPath("$.locationRef").value(equalTo(asParsedJson(location))))
                .andExpect(jsonPath("$.personRef").value(equalTo(asParsedJson(person))));

    }

    @Test
    public void getCardByIdNull() throws Exception{
        String id ="6";
        Mockito.when(service.getCard(id)).thenThrow(new CardNotFoundException("Card not found with id: " + id));

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CardNotFoundException))
                .andExpect(result -> assertEquals("Card not found with id: " + id,
                        result.getResolvedException().getMessage()));
    }

    private <T> T asParsedJson(Object obj) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(obj);
        return JsonPath.read(json, "$");
    }

}