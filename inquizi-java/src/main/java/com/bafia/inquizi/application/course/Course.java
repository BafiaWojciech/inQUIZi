package com.bafia.inquizi.application.course;

import com.bafia.inquizi.application.set.Deck;
import com.bafia.inquizi.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Entity(name="courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    @Column
    private String name;

    @Column
    boolean isClosed = false;

    @Column(unique = true)
    String accessCode;

    @OneToOne
    @JoinColumn(nullable = false, name = "teacher_id")
    private User teacher;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "courses_students",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") })
    private Set<User> students = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private List<Deck> decks = new ArrayList<>();;

}
