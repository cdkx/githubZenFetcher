package ru.eremin.githubzenfetcher.dto;

public record ZenPhraseResponse(
        String phrase,
        Long phraseCount
) {
}
