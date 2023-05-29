package com.bafia.inquizi.application.flashcard.dto;

public record FlashcardDTO(
        Long id,
        String front,
        String back,
        Long deck_id
) {
}
