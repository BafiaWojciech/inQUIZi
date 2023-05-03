package com.bafia.inquizi.application.flashcard;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;

    public FlashcardService(FlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    public void create(Flashcard flashcard) {
        Flashcard f = flashcardRepository.save(flashcard);
    }

    public List<Flashcard> findAll() {
        return new ArrayList<>(flashcardRepository.findAll());
    }


}
