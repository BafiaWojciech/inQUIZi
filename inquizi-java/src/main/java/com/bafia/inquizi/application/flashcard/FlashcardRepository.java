package com.bafia.inquizi.application.flashcard;

import com.bafia.inquizi.application.deck.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findAllByDeck(Deck deck);
}
