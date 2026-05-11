package com.himanshu.springpractice.record;

public record BookResponseDTO(Long id, String title, String author, int publicationYear, String genre, int availableCopies) {
}
