package com.example.demo.dto

import javax.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "El nombre no puede ser nulo")
    val name: String,
    @get:NotBlank(message = "La categoria no puede ser nula")
    val category: String
)