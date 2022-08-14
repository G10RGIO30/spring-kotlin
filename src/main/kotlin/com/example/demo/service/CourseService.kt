package com.example.demo.service

import com.example.demo.dto.CourseDTO
import com.example.demo.entity.Course
import com.example.demo.exception.CourseNotFoundException
import com.example.demo.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService (val courseRepository: CourseRepository) {

    companion object : KLogging()

    fun createCourse(courseDTO: CourseDTO) : CourseDTO{
        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category)
        }

        courseRepository.save(courseEntity)

        logger.info("Curso guardado $courseEntity")

        return courseEntity.let {
            CourseDTO(it.id,it.name,it.category)
        }
    }

    fun getCourses(): List<CourseDTO> {
        return courseRepository.findAll().map {
            CourseDTO(it.id,it.name,it.category)
        }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val existingCourse = courseRepository.findById(courseId)

        return if(existingCourse.isPresent){
            existingCourse.get().let {
                it.name = courseDTO.name
                it.category = courseDTO.category
                courseRepository.save(it)
                CourseDTO(it.id, it.name, it.category)
            }
        }else{
            throw CourseNotFoundException("No se encontro el curso con id: $courseId")
        }
    }

    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)

        if(existingCourse.isPresent){
            existingCourse.get().let {
                courseRepository.deleteById(courseId)
            }
        }else{
            throw CourseNotFoundException("No se encontro el curso con id: $courseId")
        }
    }

}
