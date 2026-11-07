package ru.eremin.githubzenfetcher.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import ru.eremin.githubzenfetcher.client.GitHubClient;
import ru.eremin.githubzenfetcher.service.ZenPhraseService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GitHubFetchServiceImplTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private ZenPhraseService zenPhraseService;

    @InjectMocks
    private GitHubFetchServiceImpl service;

    @Test
    void fetchAndProcessPhrase_validPhrase_savesTrimmed() {
        when(gitHubClient.getZenPhrase(anyString(), anyString(), anyString(), anyString()))
                .thenReturn("  some phrase  ");

        service.fetchAndProcessPhrase();

        verify(zenPhraseService).savePhrase("some phrase");
    }

    @ParameterizedTest(name = "Фраза [{0}] не должна сохраняться")
    @NullSource
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    void fetchAndProcessPhrase_invalidPhrase_doesNotSave(String phrase) {
        when(gitHubClient.getZenPhrase(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(phrase);

        service.fetchAndProcessPhrase();

        verifyNoInteractions(zenPhraseService);
    }

    @Test
    void fetchAndProcessPhrase_clientThrows_propagatesException() {
        when(gitHubClient.getZenPhrase(anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new RestClientException("boom"));

        assertThatThrownBy(() -> service.fetchAndProcessPhrase())
                .isInstanceOf(RestClientException.class);
        verifyNoInteractions(zenPhraseService);
    }
}