package com.bafia.inquizi.application.course.dto;

import com.bafia.inquizi.application.course.Course;
import com.bafia.inquizi.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CourseDTOMapper implements Function<Course, CourseDTO> {
    @Override
    public CourseDTO apply(Course key) {
        return new CourseDTO(
                key.getName(),
                key.getUuid(),
                key.isClosed(),
                key.getAccessCode(),
                key.getTeacher().getEmail(),
                key.getStudents()
                        .stream()
                        .map(User::getEmail)
                        .toList()
        );
    }
}
