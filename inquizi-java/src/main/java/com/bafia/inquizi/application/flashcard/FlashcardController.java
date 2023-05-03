package com.bafia.inquizi.application.flashcard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {
    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Flashcard flashcard) {
        flashcardService.create(flashcard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Flashcard>> findAll() {
        List<Flashcard> flashcardList = flashcardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(flashcardList);
    }

}
