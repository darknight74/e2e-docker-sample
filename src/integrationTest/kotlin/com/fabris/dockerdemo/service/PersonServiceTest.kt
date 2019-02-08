package com.fabris.dockerdemo.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("integration")
class PersonServiceTest {

    @Autowired
    lateinit var personService: PersonService

    @Test
    fun testSavePerson() {
        val savedFirstPerson = personService.savePerson(firstName = "John", lastName = "Doe")
        assertNotNull(savedFirstPerson.id)
        assertEquals(1L, savedFirstPerson.id)
        assertEquals("John", savedFirstPerson.firstName)
        assertEquals("Doe", savedFirstPerson.lastName)
        val savedSecondPerson = personService.savePerson("Isaac", "Newton")
        assertNotNull(savedSecondPerson.id)
        assertFalse(savedFirstPerson == savedSecondPerson)
        assertEquals(2L , savedSecondPerson.id)
        assertEquals("Isaac", savedSecondPerson.firstName)
        assertEquals("Newton", savedSecondPerson.lastName)
    }
}