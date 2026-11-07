package ru.eremin.githubzenfetcher.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eremin.githubzenfetcher.dto.ZenPhraseResponse;
import ru.eremin.githubzenfetcher.entity.ZenPhrase;
import ru.eremin.githubzenfetcher.repository.ZenPhraseRepository;
import ru.eremin.githubzenfetcher.service.ZenPhraseService;

import java.util.List;
import java.util.Optional;


@Service
public class ZenPhraseServiceImpl implements ZenPhraseService {
    private static final Logger log = LoggerFactory.getLogger(ZenPhraseServiceImpl.class);

    private final ZenPhraseRepository repository;

    public ZenPhraseServiceImpl(ZenPhraseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void savePhrase(String phrase) {
        Optional<ZenPhrase> existingPhrase = repository.findByPhrase(phrase);

        if (existingPhrase.isPresent()) {
            ZenPhrase existing = existingPhrase.get();
            existing.setPhraseCount(existing.getPhraseCount() + 1);
            repository.save(existing);
            log.info("Обновлена фраза: '{}' (count: {})", phrase, existing.getPhraseCount());
        } else {
            ZenPhrase newPhrase = new ZenPhrase(phrase);
            repository.save(newPhrase);
            log.info("Сохранена новая фраза: '{}'", phrase);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZenPhraseResponse> getAllPhrases() {
        return repository.findAllByOrderByPhraseCountDesc()
                .stream()
                .map(phrase -> new ZenPhraseResponse(phrase.getPhrase(), phrase.getPhraseCount()))
                .toList();
    }
}
