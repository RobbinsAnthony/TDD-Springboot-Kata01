package com.codedifferently.phonebook.people.exceptions;

public class PersonNotFoundException extends Exception{
    public PersonNotFoundException(String message) {
        super(message);
    }
}
