package ru.eremin.githubzenfetcher.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.eremin.githubzenfetcher.dto.ZenPhraseResponse;
import ru.eremin.githubzenfetcher.entity.ZenPhrase;
import ru.eremin.githubzenfetcher.repository.ZenPhraseRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ZenPhraseServiceImplTest {

    @Mock
    private ZenPhraseRepository repository;

    @InjectMocks
    private ZenPhraseServiceImpl service;

    @Test
    void savePhrase_newPhrase_savesWithCountOne() {
        when(repository.findByPhrase("new phrase")).thenReturn(Optional.empty());

        service.savePhrase("new phrase");

        ArgumentCaptor<ZenPhrase> captor = ArgumentCaptor.forClass(ZenPhrase.class);
        verify(repository).save(captor.capture());
        ZenPhrase saved = captor.getValue();
        assertThat(saved.getPhrase()).isEqualTo("new phrase");
        assertThat(saved.getPhraseCount()).isEqualTo(1L);
    }

    @Test
    void savePhrase_existingPhrase_incrementsCount() {
        ZenPhrase existing = new ZenPhrase("existing");
        existing.setPhraseCount(5L);
        when(repository.findByPhrase("existing")).thenReturn(Optional.of(existing));

        service.savePhrase("existing");

        verify(repository).save(existing);
        assertThat(existing.getPhraseCount()).isEqualTo(6L);
    }

    @Test
    void getAllPhrases_mapsEntityToDto() {
        ZenPhrase p1 = new ZenPhrase("a");
        p1.setPhraseCount(10L);
        ZenPhrase p2 = new ZenPhrase("b");
        p2.setPhraseCount(5L);
        when(repository.findAllByOrderByPhraseCountDesc()).thenReturn(List.of(p1, p2));

        List<ZenPhraseResponse> result = service.getAllPhrases();

        assertThat(result).containsExactly(
                new ZenPhraseResponse("a", 10L),
                new ZenPhraseResponse("b", 5L)
        );
    }

    @Test
    void getAllPhrases_empty_returnsEmptyList() {
        when(repository.findAllByOrderByPhraseCountDesc()).thenReturn(List.of());

        assertThat(service.getAllPhrases()).isEmpty();
    }
}
