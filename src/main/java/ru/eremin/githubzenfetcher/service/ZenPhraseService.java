package ru.eremin.githubzenfetcher.service;

import ru.eremin.githubzenfetcher.dto.ZenPhraseResponse;

import java.util.List;

public interface ZenPhraseService {
    void savePhrase(String phrase);

    List<ZenPhraseResponse> getAllPhrases();
}
