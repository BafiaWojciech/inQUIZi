package com.bafia.inquizi.application.course;

import com.bafia.inquizi.application.course.dto.CourseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CourseDTO> create(Principal principal, @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.create(principal, courseDTO));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAll(Principal principal) {
        return ResponseEntity.ok(courseService.getAll(principal));
    }

    @PutMapping("/{id}/open")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CourseDTO> open(Principal principal, @PathVariable String id) {
        return courseService.open(principal, id);
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> close(Principal principal, @PathVariable String id) {
        return courseService.close(principal, id);
    }

    @PutMapping("/join/{code}")
    public ResponseEntity<Void> join(Principal principal, @PathVariable String code) {
        return courseService.join(principal, code);
    }

    @PutMapping("/{id}/leave")
    public ResponseEntity<Void> leave(Principal principal, @PathVariable String id) {
        return courseService.leave(principal, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(Principal principal, @PathVariable String id) {
        return courseService.delete(principal, id);
    }
    
    @DeleteMapping("/{courseId}/students/{email}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> deleteStudent(Principal principal, @PathVariable String courseId, @PathVariable String email) {
        return courseService.deleteStudent(principal, courseId, email);
    }
}
