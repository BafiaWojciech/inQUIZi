package com.bafia.inquizi.application.course.dto;

import java.util.List;


public record CourseDTO(
        String name,
        String UUID,
        boolean isClosed,
        String accessCode,
        String teacherEmail,
        List<String> studentEmail) {
}
