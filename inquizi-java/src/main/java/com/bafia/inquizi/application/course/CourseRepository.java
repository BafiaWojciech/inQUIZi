package com.bafia.inquizi.application.course;

import com.bafia.inquizi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByUuid(String uuid);

    @Query("SELECT c FROM courses c JOIN c.students s JOIN c.teachers t WHERE t.email = :email OR s.email = :email")
    List<Course> findAllWithUsers(@Param("email") String email);
}
