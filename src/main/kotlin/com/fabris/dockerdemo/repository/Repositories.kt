package com.fabris.dockerdemo.repository

import com.fabris.dockerdemo.domain.Person
import org.springframework.data.repository.CrudRepository

interface PersonRepository: CrudRepository<Person, Long> {

    fun findByFirstName(firstName: String): List<Person>
}