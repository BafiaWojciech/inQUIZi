package com.bafia.inquizi.application.deck;

import com.bafia.inquizi.application.deck.dto.DeckDTO;
import com.bafia.inquizi.application.flashcard.dto.FlashcardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/decks")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<DeckDTO> create(Principal principal, @RequestParam long course, @RequestBody DeckDTO deckDTO) {
        return deckService.create(principal, course, deckDTO);
    }

    @GetMapping
    public ResponseEntity<List<DeckDTO>> getAll(Principal principal, @RequestParam long course) {
        return deckService.getAll(principal, course);
    }

    @PutMapping("/{id}/open")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> open(Principal principal, @PathVariable long id) {
        return deckService.setIsClosed(principal, id, false);
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> close(Principal principal, @PathVariable long id) {
        return deckService.setIsClosed(principal, id, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> delete(Principal principal, @PathVariable long id) {
        return deckService.delete(principal, id);
    }


}
