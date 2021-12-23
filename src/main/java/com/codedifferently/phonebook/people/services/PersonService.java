package com.codedifferently.phonebook.people.services;

import com.codedifferently.phonebook.people.exceptions.PersonNotFoundException;
import com.codedifferently.phonebook.people.models.Person;
import com.codedifferently.phonebook.widgets.exceptions.WidgetNotFoundException;
import com.codedifferently.phonebook.widgets.models.Widget;

import java.util.List;

public interface PersonService {
    Person create(Person person);
    Person getPersonById(Integer id) throws PersonNotFoundException;
    List<Person> getAllPeople();
    Person updatePerson(Integer id, Person person) throws PersonNotFoundException;
    Boolean deletePerson(Integer id) throws PersonNotFoundException;
}
