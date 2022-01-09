package eu.exadelpractice.registry.events.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import eu.exadelpractice.registry.events.exception.EventNotFoundException;
import eu.exadelpractice.registry.events.model.Event;
import eu.exadelpractice.registry.events.model.LocationRef;
import eu.exadelpractice.registry.events.model.WorkerRef;
import eu.exadelpractice.registry.common.model.ErrorHelper;
import eu.exadelpractice.registry.events.MainTestClass;
import eu.exadelpractice.registry.events.service.impl.EventServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

//@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = MainTestClass.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventServiceImpl service;

    @MockBean
    private ErrorHelper he;

    @Test
    public void getEventById() throws Exception{
        LocationRef location=new LocationRef("3543","location title");
        WorkerRef person=new WorkerRef("12345","Vardenis","Pavardenis");

        List<WorkerRef> workerRefList = new ArrayList<WorkerRef>();
        workerRefList.add(person);

        Event mockEvent=new Event("1","Name","Description",location, workerRefList);

        Mockito.when(service.getEvent("1")).thenReturn(mockEvent);
        mockMvc.perform(MockMvcRequestBuilders.get("/events/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.description",is("Description")))
                .andExpect(jsonPath("$.locationRef").value(equalTo(asParsedJson(location))))
                .andExpect(jsonPath("$.personRef").value(equalTo(asParsedJson(workerRefList))));
    }

    @Test
    public void getCardbyNullId() throws Exception{
        String id="6";
        Mockito.when(service.getEvent(id))
                .thenThrow(new EventNotFoundException("Event not found with id: " + id));

        mockMvc.perform(MockMvcRequestBuilders.get("/events/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EventNotFoundException))
                .andExpect(result -> assertEquals("Event not found with id: " + id,
                        result.getResolvedException().getMessage()));
    }

    private <T> T asParsedJson(Object obj) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(obj);
        return JsonPath.read(json, "$");
    }
}