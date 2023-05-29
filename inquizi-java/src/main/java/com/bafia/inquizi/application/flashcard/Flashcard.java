package com.bafia.inquizi.application.flashcard;

import com.bafia.inquizi.application.course.Course;
import com.bafia.inquizi.application.deck.Deck;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="flashcards")
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String front;
    private String back;

    @ManyToOne
    @JoinColumn(nullable = false, name = "deck_id")
    private Deck deck;
}
