package com.bafia.inquizi.application.deck.dto;

import java.util.List;

public record DeckDTO(
        Long id,
        String name,
        boolean isClosed,
        Long courseId)
{
}
