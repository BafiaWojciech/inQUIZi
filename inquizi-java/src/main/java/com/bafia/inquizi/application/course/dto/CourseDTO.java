package com.bafia.inquizi.application.course.dto;

import java.util.List;


public record CourseDTO(
        Long id,
        String name,
        boolean isClosed,
        String accessCode,
        String teacherEmail,
        List<String> studentEmail) {
}
