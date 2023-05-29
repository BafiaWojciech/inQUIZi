package com.bafia.inquizi.application.flashcard;

import com.bafia.inquizi.application.flashcard.dto.FlashcardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {
    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> create(Principal principal, @RequestBody FlashcardDTO flashcard, @RequestParam long deck) {
        return flashcardService.create(principal, flashcard, deck);
    }

    @GetMapping
    public ResponseEntity<List<FlashcardDTO>> findAll(Principal principal, @RequestParam long deck) {
        return flashcardService.findAll(principal, deck);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> edit(Principal principal, @RequestBody FlashcardDTO flashcard, @RequestParam long deck) {
        return flashcardService.edit(principal, flashcard, deck);
    }

    @DeleteMapping("/{flashcard_id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(Principal principal, @PathVariable long flashcard_id) {
        return flashcardService.delete(principal, flashcard_id);
    }
}
