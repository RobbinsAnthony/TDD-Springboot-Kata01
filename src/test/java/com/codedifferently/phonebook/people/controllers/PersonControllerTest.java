package com.codedifferently.phonebook.people.controllers;

import com.codedifferently.phonebook.BaseControllerTest;
import com.codedifferently.phonebook.people.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.people.models.Person;
import com.codedifferently.phonebook.people.models.PhoneNumber;
import com.codedifferently.phonebook.people.services.PersonService;
import com.codedifferently.phonebook.widgets.exceptions.WidgetNotFoundException;
import com.codedifferently.phonebook.widgets.models.Widget;
import com.codedifferently.phonebook.widgets.models.WidgetPart;
import com.codedifferently.phonebook.widgets.services.WidgetService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest//test the container
@AutoConfigureMockMvc //create autoconfiguration for testing container
@ExtendWith(SpringExtension.class)//for junit5 want junit to work with sprint contents
public class PersonControllerTest extends BaseControllerTest {

    @MockBean//our service
    private PersonService mockPersonService;

    @Autowired
    private MockMvc mockMvc;

    private Person inputPerson;
    private Person mockResponsePerson1;
    private Person mockResponsePerson2;

    @BeforeEach
    public void setUp() {
        List<PhoneNumber> number = new ArrayList<>();
        number.add(new PhoneNumber("kim"));
        number.add(new PhoneNumber("Part 2"));
        inputPerson = new Person("Kim","Tim", number);

        mockResponsePerson1 = new Person("Tariq","hook", number);
        mockResponsePerson1.setId(1);

        mockResponsePerson2 = new Person("Rocket","Power", number);
        mockResponsePerson2.setId(2);


    }

    @Test
    @DisplayName("Person Post: /Person - success")
    public void createPersonRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponsePerson1).when(mockPersonService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/Person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputPerson)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Test Person 01")));
    }

    @Test
    @DisplayName("GET /person/1 - Found")
    public void getPersonByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponsePerson1).when(mockPersonService).getPersonById(1);

        mockMvc.perform(get("/person/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Person 01")));
    }

    @Test
    @DisplayName("GET /person/1 - Not Found")
    public void getPersonByIdTestFail() throws Exception {
        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(mockPersonService).getPersonById(1);
        mockMvc.perform(get("/person/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /person/1 - Success")
    public void putPersonTestNotSuccess() throws Exception{
        List<PhoneNumber> number = new ArrayList<>();
        number.add(new PhoneNumber("number "));
        number.add(new PhoneNumber("number"));
        Person expectedPersonUpdate = new Person("Jasmine","Durham", number);
        expectedPersonUpdate.setId(1);
        BDDMockito.doReturn(expectedPersonUpdate).when(mockPersonService).updatePerson(any(), any());
        mockMvc.perform(put("/person/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputPerson)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("After Update Person")));
    }

    @Test
    @DisplayName("PUT /person/1 - Not Found")
    public void putPersonTestNotFound() throws Exception{
        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(mockPersonService).updatePerson(any(), any());
        mockMvc.perform(put("/person/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputPerson)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /person/1 - Success")
    public void deletePersonTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockPersonService).deletePerson(any());
        mockMvc.perform(delete("/person/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /person/1 - Not Found")
    public void deletePersonTestNotFound() throws Exception{
        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(mockPersonService).deletePerson(any());
        mockMvc.perform(delete("/person/{id}", 1))
                .andExpect(status().isNotFound());
    }
}


