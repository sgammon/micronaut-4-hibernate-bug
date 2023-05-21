package com.example.model

import io.micronaut.core.annotation.Introspected
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Introspected
@Entity(name = "example")
data class ExampleModel(
    @Id @GeneratedValue @Column(name = "id") val id: Long,
    @Column(name = "message") val message: String,
)
