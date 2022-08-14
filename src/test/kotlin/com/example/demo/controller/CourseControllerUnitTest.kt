package com.example.demo.controller

import com.example.demo.dto.CourseDTO
import com.example.demo.entity.Course
import com.example.demo.service.CourseService
import com.example.demo.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    @Test
    fun createCourse() {
        val courseDTO = CourseDTO(null, "Test course", "Test category")

        every { courseService.createCourse(any()) } returns courseDTO(id = 1)

        val responseBody = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            responseBody!!.id != null
        }
    }

    @Test
    fun createCourseValidation() {
        val courseDTO = CourseDTO(null, "", "")

        every { courseService.createCourse(any()) } returns courseDTO(id = 1)

        val responseBody = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest

    }

    @Test
    fun getCourses() {
        every { courseService.getCourses() }.returnsMany(
                listOf(courseDTO(1,"Java 8","Development"),
                courseDTO(2,"Angular", "Development")
                )
        )

        val responseBody = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(2, responseBody!!.size)
    }

    @Test
    fun updateCourse() {

        every { courseService.updateCourse(any(),any()) } returns courseDTO(1,
            "Apache Kafka for Developers using Spring Boot1",
            "Development")

        val updatedCourseEntity = Course(
            null,
            "Apache Kafka for Developers using Spring Boot1", "Development"
        )

        val updatedCourseDTO = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 1)
            .bodyValue(updatedCourseEntity)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Apache Kafka for Developers using Spring Boot1", updatedCourseDTO?.name)

    }

    @Test
    fun deleteCourse() {

        every { courseService.deleteCourse(any()) } just runs


        val updatedCourseDTO = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent

    }

}