package com.codedifferently.phonebook.person.models;

import com.codedifferently.phonebook.people.models.Person;
import com.codedifferently.phonebook.people.models.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private Person person1;
    private Person person2;

    private Person emptyPerson1;
    private Person emptyPerson2;

    @BeforeEach
    public void setUp() {
        emptyPerson1 = new Person();
        emptyPerson2 = new Person();

        List<PhoneNumber> number = new ArrayList<>();
        number.add(new PhoneNumber("Number 1"));
        number.add(new PhoneNumber("Number 2"));

        person1 = new Person("Anthony", "Robbins", number);
        person1.setId(1);

        person2 = new Person("Jasmine", "Durham", number);
        person2.setId(2);
    }

    @Test
    public void testEmptyEquals() throws Exception {

        assertTrue(
                emptyPerson1.equals(emptyPerson2),
                "Both empty Person instances should be equal");
    }

    @Test
    public void testContentEquals() throws Exception {

        assertTrue(
                person1.equals(person2),
                "Both non-empty Person instances should be equal");
    }

    @Test
    public void testNotEquals() throws Exception {

        assertFalse(
                emptyPerson1.equals(person2),
                "The Person instances should not be equal");
    }

    @Test
    public void testEmptyHashCode() throws Exception {

        assertEquals(
                emptyPerson1.hashCode(),
                emptyPerson2.hashCode(),
                "Both empty Person instances should have the same hashCode");
    }

    @Test
    public void testContentHashCode() throws Exception {

        assertEquals(
                person1.hashCode(),
                person2.hashCode(),
                "Both non-empty Person instances should have the same hashCode");
    }

    @Test
    public void testHashCode() throws Exception {

        assertNotEquals(
                emptyPerson1.hashCode(),
                person2.hashCode(),
                "The Person instances should not have the same hashCode");
    }

    @Test
    public void testEmptyToString() throws Exception {

        assertEquals(
                emptyPerson1.toString(),
                emptyPerson2.toString(),
                "Both empty Person instances should have the same toString");
    }

    @Test
    public void testContentToString() throws Exception {

        assertEquals(
                person1.toString(),
                person2.toString(),
                "Both non-empty Person instances should have the same toString");
    }

    @Test
    public void testNotToString() throws Exception {

        assertNotEquals(
                emptyPerson1.toString(),
                person2.toString(),
                "The Person instances should not have the same toString");
    }







}
