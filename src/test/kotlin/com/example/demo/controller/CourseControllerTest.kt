package com.example.demo.controller

import com.example.demo.dto.CourseDTO
import com.example.demo.entity.Course
import com.example.demo.repository.CourseRepository
import com.example.demo.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CourseControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()

        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun createCourse() {
        val courseDTO = CourseDTO(null, "Test course", "Test category")

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
    fun getCourses() {
        val responseBody = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(3, responseBody!!.size)
    }

    @Test
    fun updateCourse() {

        val courseEntity = Course(
            null,
            "Apache Kafka for Developers using Spring Boot", "Development",
        )
        courseRepository.save(courseEntity)
        val updatedCourseEntity = Course(
            null,
            "Apache Kafka for Developers using Spring Boot1", "Development"
        )

        val updatedCourseDTO = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", courseEntity.id)
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

        val courseEntity = Course(
            null,
            "Apache Kafka for Developers using Spring Boot", "Development",
        )
        courseRepository.save(courseEntity)


        val updatedCourseDTO = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", courseEntity.id)
            .exchange()
            .expectStatus().isNoContent

    }

}