package com.bafia.inquizi.application.deck;

import com.bafia.inquizi.application.course.Course;
import com.bafia.inquizi.application.flashcard.Flashcard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name="decks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    boolean isClosed = false;

    @ManyToOne
    @JoinColumn(nullable = false, name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "deck")
    private List<Flashcard> flashcards = new ArrayList<>();

}
