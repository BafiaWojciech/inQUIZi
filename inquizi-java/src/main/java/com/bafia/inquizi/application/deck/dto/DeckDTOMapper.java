package com.bafia.inquizi.application.deck.dto;

import com.bafia.inquizi.application.deck.Deck;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DeckDTOMapper implements Function<Deck, DeckDTO> {
    @Override
    public DeckDTO apply(Deck key) {
        return new DeckDTO(
                key.getId(),
                key.getName(),
                key.isClosed(),
                key.getCourse().getId()
        );
    }
}
