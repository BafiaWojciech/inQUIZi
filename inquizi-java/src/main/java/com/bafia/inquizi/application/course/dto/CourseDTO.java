package com.bafia.inquizi.application.course.dto;

import com.bafia.inquizi.user.User;

import java.util.List;


public record CourseDTO(
        String name,
        String UUID,
        List<String> teacherEmail,
        List<String> studentEmail
) {
}
