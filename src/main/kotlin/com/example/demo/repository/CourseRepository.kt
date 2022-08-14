package com.example.demo.repository

import com.example.demo.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

}