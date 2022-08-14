package com.example.demo.controller

import com.example.demo.dto.CourseDTO
import com.example.demo.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCourse(@RequestBody @Valid courseDTO: CourseDTO): CourseDTO {
        return courseService.createCourse(courseDTO)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getCourses() : List<CourseDTO> = courseService.getCourses()

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Int, @RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.updateCourse(courseId, courseDTO)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCourse(@PathVariable courseId: Int) {
        return courseService.deleteCourse(courseId)
    }
}