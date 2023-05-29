package com.bafia.inquizi.application.flashcard.dto;

import com.bafia.inquizi.application.deck.Deck;
import com.bafia.inquizi.application.deck.dto.DeckDTO;
import com.bafia.inquizi.application.flashcard.Flashcard;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FlashcardDTOMapper implements Function<Flashcard, FlashcardDTO> {
    @Override
    public FlashcardDTO apply(Flashcard key) {
        return new FlashcardDTO(
                key.getId(),
                key.getFront(),
                key.getBack(),
                key.getDeck().getId()
        );
    }
}