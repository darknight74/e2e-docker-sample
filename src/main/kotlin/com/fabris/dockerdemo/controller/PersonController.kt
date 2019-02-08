package com.fabris.dockerdemo.controller

import com.fabris.dockerdemo.domain.Person
import com.fabris.dockerdemo.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/persons")
class PersonController @Autowired constructor(private val personService: PersonService){

    @PostMapping
    fun savePerson(@RequestBody person: PersonDto): PersonDto {
        return mapPersonToPersonDto(personService.savePerson(person.firstName, person.lastName))
    }

    @GetMapping("{id}")
    fun getPersonById(@PathVariable(name = "id") id: Long): PersonDto {
        return mapPersonToPersonDto(personService.getPersonById(id))
    }

    @GetMapping
    fun getPersonByFirstName(@RequestParam("name") name: String): List<PersonDto> {
        val persons = personService.getPersonByName(name)
        return persons.map { mapPersonToPersonDto(it) }
    }
}

data class PersonDto(val id: Long = 0L, val firstName: String, val lastName: String)

fun mapPersonToPersonDto(person: Person): PersonDto {
    return PersonDto(person.id, person.firstName, person.lastName)
}

fun mapPersonDtoToPerson(personDto: PersonDto): Person {
    return Person(personDto.id, personDto.firstName, personDto.lastName)
}