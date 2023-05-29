package com.bafia.inquizi.application.deck;

import com.bafia.inquizi.application.course.Course;
import com.bafia.inquizi.application.course.CourseRepository;
import com.bafia.inquizi.application.deck.dto.DeckDTO;
import com.bafia.inquizi.application.deck.dto.DeckDTOMapper;
import com.bafia.inquizi.application.flashcard.dto.FlashcardDTO;
import com.bafia.inquizi.application.flashcard.dto.FlashcardDTOMapper;
import com.bafia.inquizi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeckService {
    private final DeckRepository deckRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DeckDTOMapper deckMapper;
    private final FlashcardDTOMapper flashcardMapper;

    public ResponseEntity<DeckDTO> create(Principal principal, Long course_id, DeckDTO deckDTO) {
        if(courseRepository.findById(course_id).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Course course = courseRepository.findById(course_id).get();
        if(!hasTeacherAccess(course, principal))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Deck deck = new Deck();
        deck.setCourse(course);
        deck.setName(deckDTO.name());
        deck.setClosed(false);
        deckRepository.save(deck);
        return ResponseEntity.status(HttpStatus.OK).body(deckMapper.apply(deck));
    }

    public ResponseEntity<List<DeckDTO>> getAll(Principal principal, Long course_id) {
        if(courseRepository.findById(course_id).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Course course = courseRepository.findById(course_id).get();
        List<Deck> decks;
        if(hasTeacherAccess(course, principal))
            decks = deckRepository.findAllByCourseId(course_id);
        else if(hasStudentAccess(course, principal))
            decks = deckRepository.findAllOpenByCourseId(course_id);
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.OK).body(decks.stream().map(deckMapper).collect(Collectors.toList()));
    }

    public ResponseEntity<Void> setIsClosed(Principal principal, Long id, boolean value) {
        if(deckRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Deck deck = deckRepository.findById(id).get();
        if(!hasTeacherAccess(deck, principal))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        deck.setClosed(value);
        deckRepository.save(deck);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<Void> delete(Principal principal, Long id) {
        if(deckRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Deck deck = deckRepository.findById(id).get();
        if(!hasTeacherAccess(deck, principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        deckRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    private boolean hasTeacherAccess(Deck deck, Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(deckRepository.findById(deck.getId()).isEmpty() || userRepository.findUserByEmail(email).isEmpty())
            return false;
        return deck.getCourse().getTeacher().equals(userRepository.findUserByEmail(email).get());
    }

    private boolean hasTeacherAccess(Course course, Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(courseRepository.findById(course.getId()).isEmpty() || userRepository.findUserByEmail(email).isEmpty())
            return false;
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

    private boolean hasStudentAccess(Course course, Principal principal) {
        String email = (String) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(courseRepository.findById(course.getId()).isEmpty() || userRepository.findUserByEmail(email).isEmpty())
            return false;
        return course.getStudents().contains(userRepository.findUserByEmail(email).get());
    }


}
