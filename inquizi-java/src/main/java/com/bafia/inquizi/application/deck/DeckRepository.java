package com.bafia.inquizi.application.deck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findAllByCourseId(Long courseId);

    @Query("SELECT d FROM decks d WHERE d.isClosed=false")
    List<Deck> findAllOpenByCourseId(Long courseId);
}
