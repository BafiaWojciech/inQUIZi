package com.bafia.inquizi.application.course;

import com.bafia.inquizi.application.course.dto.CourseDTO;
import com.bafia.inquizi.application.course.dto.CourseDTOMapper;
import com.bafia.inquizi.user.User;
import com.bafia.inquizi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseDTOMapper mapper;

    public CourseDTO create(Principal principal, CourseDTO courseDTO) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        Set<User> teachers = new HashSet<>();
        Set<User> students = new HashSet<>();

        if (userRepository.findUserByEmail(email).isPresent()) {
            User tmp = userRepository.findUserByEmail(email).get();
            teachers.add(tmp);
        } else return null;

        if (courseDTO.studentEmail() != null) {
            for (var i : courseDTO.studentEmail())
                if (userRepository.findUserByEmail(i).isPresent())
                    students.add(userRepository.findUserByEmail(i).get());
        }
        Course course = new Course();
        course.setName(courseDTO.name());
        course.setTeachers(teachers);
        course.setStudents(students);
        course.setAccessToCourse(AccessToCourse.CLOSED);
        courseRepository.save(course);
        return mapper.apply(course);
    }

    public List<CourseDTO> getAll(Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<Course> result = courseRepository.findAllWithUsers(email);
        return result
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<Void> leave(Principal principal, String id) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            Course c = courseRepository.findCourseByUuid(id).get();
            //usunięcie kursu przez nauczyciela
            if (c.getTeachers().contains(userRepository.findUserByEmail(email).get())) {
                if (c.getTeachers().size() == 1) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                } else {
                    c.getTeachers().remove(userRepository.findUserByEmail(email).get());
                    courseRepository.save(c);
                }
            }
            //opuszczenie kursu przez ucznia
            if (c.getStudents().contains(userRepository.findUserByEmail(email).get())) {
                c.getStudents().remove(userRepository.findUserByEmail(email).get());
                courseRepository.save(c);
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity<Void> delete(Principal principal, String id) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            courseRepository.delete(courseRepository.findCourseByUuid(id).get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<Void> deleteStudent(Principal principal, String id, String email) {
        String teacherEmail = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent() || userRepository.findUserByEmail(teacherEmail).isPresent()) {
            if (!courseRepository.findCourseByUuid(id).get().getTeachers().contains(userRepository.findUserByEmail(teacherEmail).get())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Course c = courseRepository.findCourseByUuid(id).get();
            c.getStudents().remove(userRepository.findUserByEmail(email).get());
            courseRepository.save(c);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> changeAccess(Principal principal, String id, CourseDTO courseDTO) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            Course c = courseRepository.findCourseByUuid(id).get();
            if (!c.getTeachers().contains(userRepository.findUserByEmail(email).get())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            c.setAccessToCourse(AccessToCourse.valueOf(courseDTO.accessToCourse().toString()));
            courseRepository.save(c);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> join(Principal principal, String id) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            Course c = courseRepository.findCourseByUuid(id).get();
            if (c.getAccessToCourse() == AccessToCourse.PUBLIC) {
                c.getStudents().add(userRepository.findUserByEmail(email).get());
                courseRepository.save(c);
                return ResponseEntity.status(HttpStatus.OK).build();
            } else if (c.getAccessToCourse() == AccessToCourse.CLOSED) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else if (c.getAccessToCourse() == AccessToCourse.PASSWORD) {
                //TODO
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
            }
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
