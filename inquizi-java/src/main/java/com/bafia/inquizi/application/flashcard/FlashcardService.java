package com.bafia.inquizi.application.flashcard;

import com.bafia.inquizi.application.course.Course;
import com.bafia.inquizi.application.course.CourseRepository;
import com.bafia.inquizi.application.deck.Deck;
import com.bafia.inquizi.application.deck.DeckRepository;
import com.bafia.inquizi.application.flashcard.dto.FlashcardDTO;
import com.bafia.inquizi.application.flashcard.dto.FlashcardDTOMapper;
import com.bafia.inquizi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final FlashcardDTOMapper flashcardMapper;
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public ResponseEntity<Void> create(Principal principal, FlashcardDTO flashcard, Long deckId) {
        if (deckRepository.findById(deckId).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Deck deck = deckRepository.findById(deckId).get();
        if (!hasTeacherAccess(deck, principal))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Flashcard f = new Flashcard();
        f.setFront(flashcard.front());
        f.setBack(flashcard.back());
        f.setDeck(deck);
        flashcardRepository.save(f);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<List<FlashcardDTO>> findAll(Principal principal, Long deckId) {
        if (deckRepository.findById(deckId).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Deck deck = deckRepository.findById(deckId).get();
        if (!hasTeacherAccess(deck, principal) && !hasStudentAccess(deck, principal))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(flashcardRepository.findAllByDeck(deck).stream().map(flashcardMapper).collect(Collectors.toList()));
    }

    public ResponseEntity<Void> edit(Principal principal, FlashcardDTO flashcard, long deck) {
        if (deckRepository.findById(deck).isEmpty() || flashcardRepository.findById(flashcard.id()).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Flashcard f = flashcardRepository.findById(flashcard.id()).get();
        if (!hasTeacherAccess(f.getDeck(), principal))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (flashcard.deck_id() != null) {
            if (deckRepository.findById(flashcard.deck_id()).isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            Deck d = deckRepository.findById(flashcard.deck_id()).get();
            if (!hasTeacherAccess(d, principal))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            f.setDeck(d);
        }
        f.setFront(flashcard.front());
        f.setBack(flashcard.back());

        flashcardRepository.save(f);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> delete(Principal principal, long flashcard_id) {
        if (flashcardRepository.findById(flashcard_id).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (!hasTeacherAccess(flashcardRepository.findById(flashcard_id).get(), principal))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        flashcardRepository.deleteById(flashcard_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    private boolean hasTeacherAccess(Deck deck, Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (deckRepository.findById(deck.getId()).isEmpty() || userRepository.findUserByEmail(email).isEmpty())
            return false;
        return deck.getCourse().getTeacher().equals(userRepository.findUserByEmail(email).get());
    }

    private boolean hasTeacherAccess(Flashcard flashcard, Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (flashcardRepository.findById(flashcard.getId()).isEmpty()
                || deckRepository.findById(flashcard.getDeck().getId()).isEmpty()
                || userRepository.findUserByEmail(email).isEmpty())
            return false;
        Deck deck = deckRepository.findById(flashcard.getDeck().getId()).get();
        if (courseRepository.findById(deck.getCourse().getId()).isEmpty())
            return false;
        Course course = courseRepository.findById(deck.getCourse().getId()).get();
        return course.getTeacher().equals(userRepository.findUserByEmail(email).get());
    }

    private boolean hasStudentAccess(Deck deck, Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (deckRepository.findById(deck.getId()).isEmpty()
                || deckRepository.findById(deck.getId()).get().isClosed()
                || userRepository.findUserByEmail(email).isEmpty())
            return false;
        return deck.getCourse().getStudents().contains(userRepository.findUserByEmail(email).get());
    }

    private boolean hasStudentAccess(Flashcard flashcard, Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (flashcardRepository.findById(flashcard.getId()).isEmpty()
                || deckRepository.findById(flashcard.getDeck().getId()).isEmpty()
                || userRepository.findUserByEmail(email).isEmpty())
            return false;
        Deck deck = deckRepository.findById(flashcard.getDeck().getId()).get();
        if (deck.isClosed() || courseRepository.findById(deck.getCourse().getId()).isEmpty())
            return false;
        Course course = courseRepository.findById(deck.getCourse().getId()).get();
        return course.getStudents().contains(userRepository.findUserByEmail(email).get());
    }



}
