package com.example.demo.controller

import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController {

    companion object : KLogging()

    @GetMapping
    fun getGreeting() : ResponseEntity<String> {
        logger.info("Ingreso peticion")
        return ResponseEntity.ok().body("Saludos desde Spring con kotlin")
    }
}