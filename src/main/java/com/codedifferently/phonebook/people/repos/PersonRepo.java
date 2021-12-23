package com.codedifferently.phonebook.people.repos;

import com.codedifferently.phonebook.people.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, Integer> {
    Optional<Person> findPersonByLastName( String lastName);
}
