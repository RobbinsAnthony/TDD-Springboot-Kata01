package com.codedifferently.phonebook.person.services;


import com.codedifferently.phonebook.people.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.people.models.Person;
import com.codedifferently.phonebook.people.models.PhoneNumber;
import com.codedifferently.phonebook.people.repos.PersonRepo;
import com.codedifferently.phonebook.people.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {


        @MockBean// fake place to store
        private PersonRepo mockPersonRepo;

        @Autowired
        private PersonService personService;

        private Person inputPerson;
        private Person mockResponsePerson1;
        private Person mockResponsePerson2;

        @BeforeEach
        public void setUp() {
            List<PhoneNumber> number = new ArrayList<>();
            number.add(new PhoneNumber("Number 1"));
            number.add(new PhoneNumber("Number 2"));
            inputPerson = new Person("Anthony", "Robbins", number);

            mockResponsePerson1 = new Person("Santana", "Clause", number);
            mockResponsePerson1.setId(1);

            mockResponsePerson2 = new Person("Margaret", "Jenkins", number);
            mockResponsePerson2.setId(2);
        }

        @Test
        @DisplayName("Person Service: Create Person- Success")
        public void createPersonTestSuccess() {
            BDDMockito.doReturn(mockResponsePerson1).when(mockPersonRepo).save(ArgumentMatchers.any());
            Person returnedPerson = personService.create(inputPerson);
            Assertions.assertNotNull(returnedPerson, "Person should not be null");
            Assertions.assertEquals(returnedPerson.getId(), 1);
        }

        @Test
        @DisplayName("Person Service: Get Widget by Id - Success")
        public void getPersonByIdTestSuccess() throws PersonNotFoundException {
            BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(inputPerson.getId());
            Person foundPerson = personService.getPersonById(inputPerson.getId());
            Assertions.assertEquals(mockResponsePerson1.toString(), foundPerson.toString());
        }

        @Test
        @DisplayName("Person Service: Get Person by Id - Fail")
        public void getPersonByIdTestFailed() {
            BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
            Assertions.assertThrows(PersonNotFoundException.class, () -> {
                personService.getPersonById(1);
            });
        }

        @Test
        @DisplayName("Person Service: Get All People - Success")
        public void getAllPeopleTestSuccess(){
            List<Person> people = new ArrayList<>();
            people.add(mockResponsePerson1);
            people.add(mockResponsePerson2);

            BDDMockito.doReturn(people).when(mockPersonRepo).findAll();

            List<Person> responsePeople = personService.getAllPeople();
            Assertions.assertIterableEquals(people,responsePeople);
        }

        @Test
        @DisplayName("Person Service: Update Person - Success")
        public void updatePersonTestSuccess() throws PersonNotFoundException {
            List<PhoneNumber> numbers = new ArrayList<>();
            numbers.add(new PhoneNumber("Part 1"));
            numbers.add(new PhoneNumber("Part 2"));
            Person expectedPersonUpdate = new Person("Tariq", "Hook", numbers);

            BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
            BDDMockito.doReturn(expectedPersonUpdate).when(mockPersonRepo).save(ArgumentMatchers.any());

            Person actualPerson = personService.updatePerson(1, expectedPersonUpdate);
            Assertions.assertEquals(expectedPersonUpdate.toString(), actualPerson.toString());
        }

        @Test
        @DisplayName("Person Service: Update Person - Fail")
        public void updatePersonTestFail()  {
            List<PhoneNumber> numbers = new ArrayList<>();
            numbers.add(new PhoneNumber("Part 1"));
            numbers.add(new PhoneNumber("Part 2"));
            Person expectedPersonUpdate = new Person("Steph", "Eldridge",  numbers);
            BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
            Assertions.assertThrows(PersonNotFoundException.class, ()-> {
                personService.updatePerson(1, expectedPersonUpdate);
            });
        }

        @Test
        @DisplayName("Person Service: Delete Person - Success")
        public void deletePersonTestSuccess() throws PersonNotFoundException {
            BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
            Boolean actualResponse = personService.deletePerson(1);
            Assertions.assertTrue(actualResponse);
        }

        @Test
        @DisplayName("Person Service: Delete Person - Fail")
        public void deletePersonTestFail()  {
            BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
            Assertions.assertThrows(PersonNotFoundException.class, ()-> {
                personService.deletePerson(1);
            });
        }

}
