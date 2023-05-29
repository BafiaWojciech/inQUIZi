package com.bafia.inquizi.application.course;

import com.bafia.inquizi.application.deck.Deck;
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

    @Column
    private String name;

    @Column
    boolean isClosed = false;

    @Column(unique = true)
    String accessCode;

    @OneToMany(mappedBy = "course")
    private List<Deck> decks = new ArrayList<>();

    @OneToOne
    @JoinColumn(nullable = false, name = "teacher_id")
    private User teacher;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(name = "courses_students",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") })
    private Set<User> students = new HashSet<>();
}
