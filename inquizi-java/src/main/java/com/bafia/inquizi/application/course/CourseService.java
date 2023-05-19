package com.bafia.inquizi.application.course;

import com.bafia.inquizi.application.course.dto.CourseDTO;
import com.bafia.inquizi.application.course.dto.CourseDTOMapper;
import com.bafia.inquizi.register.confirmation.ConfirmationCode;
import com.bafia.inquizi.user.User;
import com.bafia.inquizi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseDTOMapper mapper;

    public CourseDTO create(Principal principal, CourseDTO courseDTO) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        User teacher = userRepository.findUserByEmail(email).get();
        Course course = new Course();
        course.setName(courseDTO.name());
        course.setTeacher(teacher);
        course.setClosed(true);
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

    public ResponseEntity<Void> leave(Principal principal, String id) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            Course course = courseRepository.findCourseByUuid(id).get();
            if (course.getTeacher() != userRepository.findUserByEmail(email).get()) {
                course.getStudents().remove(userRepository.findUserByEmail(email).get());
                courseRepository.save(course);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> delete(Principal principal, String id) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            courseRepository.delete(courseRepository.findCourseByUuid(id).get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> deleteStudent(Principal principal, String id, String email) {
        String teacherEmail = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent() || userRepository.findUserByEmail(teacherEmail).isPresent()) {
            Course c = courseRepository.findCourseByUuid(id).get();
            if (c.getTeacher().getEmail().equals(teacherEmail)) {
                c.getStudents().remove(userRepository.findUserByEmail(email).get());
                courseRepository.save(c);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    public ResponseEntity<Void> join(Principal principal, String code) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByAccessCode(code).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            Course c = courseRepository.findCourseByAccessCode(code).get();
            User u = userRepository.findUserByEmail(email).get();
            if(c.getStudents().contains(u) || c.getTeacher() == u)
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            c.getStudents().add(u);
            courseRepository.save(c);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<CourseDTO>open(Principal principal, String id) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByUuid(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            Course c = courseRepository.findCourseByUuid(id).get();
            c.setClosed(false);
            c.setAccessCode(generateAccessCode());
            courseRepository.save(c);
            CourseDTO dto = mapper.apply(c);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> close(Principal principal, String id) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (courseRepository.findCourseByAccessCode(id).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            Course c = courseRepository.findCourseByAccessCode(id).get();
            c.setClosed(true);
            c.setAccessCode(null);
            courseRepository.save(c);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private String generateAccessCode() {
        String numbers = "0123456789";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb;
        do {
            List<Character> chars = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                chars.add(numbers.charAt(random.nextInt(numbers.length())));
            for (int i = 0; i < 2; i++)
                chars.add(alphabet.charAt(random.nextInt(alphabet.length())));

            Collections.shuffle(chars, random);

            sb = new StringBuilder();
            for (char c : chars) {
                sb.append(c);
            }
        } while(courseRepository.findCourseByAccessCode(sb.toString()).isPresent());
        return sb.toString();
    }
}
