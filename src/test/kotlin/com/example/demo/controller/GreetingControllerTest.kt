package com.example.demo.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GreetingControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun getGreeting() {
        val result = webTestClient.get()
            .uri("/v1/greetings")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        //Assertions.assertEquals("Saludos desde Spring con Kotlin", result)
    }
}