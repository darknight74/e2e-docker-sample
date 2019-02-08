package com.fabris.dockerdemo.service

import com.fabris.dockerdemo.domain.Person
import com.fabris.dockerdemo.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Component
class PersonService @Autowired constructor(private val personRepository: PersonRepository) {

    @Transactional
    fun savePerson(firstName: String, lastName: String): Person {
        val person = Person(firstName = firstName, lastName = lastName)
        return personRepository.save(person)
    }

    @Transactional
    fun getPersonByName(firstName: String): List<Person> {
        return personRepository.findByFirstName(firstName)
    }

    @Transactional
    fun getPersonById(id: Long): Person {
        return personRepository.findById(id).orElseThrow {EntityNotFoundException("Person with id $id not found") }
    }

}