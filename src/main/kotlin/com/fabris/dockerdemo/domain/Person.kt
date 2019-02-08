package com.fabris.dockerdemo.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "person")
data class Person(
        @Id
        @GeneratedValue
        val id: Long = 0L,

        @Column(name = "first_name")
        val firstName: String,

        @Column(name = "last_name")
        val lastName: String
)