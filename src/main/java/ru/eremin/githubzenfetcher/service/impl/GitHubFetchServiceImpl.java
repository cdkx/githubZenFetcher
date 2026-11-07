package ru.eremin.githubzenfetcher.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.eremin.githubzenfetcher.client.GitHubClient;
import ru.eremin.githubzenfetcher.service.GitHubFetchService;
import ru.eremin.githubzenfetcher.service.ZenPhraseService;


@Service
public class GitHubFetchServiceImpl implements GitHubFetchService {
    private static final Logger log = LoggerFactory.getLogger(GitHubFetchServiceImpl.class);
    private final GitHubClient gitHubClient;
    private final ZenPhraseService zenPhraseService;

    @Value("${github.token}")
    private String githubToken;

    public GitHubFetchServiceImpl(GitHubClient gitHubClient, ZenPhraseService zenPhraseService) {
        this.gitHubClient = gitHubClient;
        this.zenPhraseService = zenPhraseService;
    }

    @Scheduled(fixedDelay = 5000)
    @Override
    @Retryable(
            retryFor = {RestClientException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void fetchAndProcessPhrase() {
        String authorization = "Bearer " + githubToken;
        String apiVersion = "2026-03-10";
        String userAgent = "GithubZenFetcher/1.0";
        String accept = "*/*";

        String phrase = gitHubClient.getZenPhrase(authorization, apiVersion, userAgent, accept);

        if (phrase != null && !phrase.trim().isEmpty()) {
            zenPhraseService.savePhrase(phrase.trim());
        } else {
            log.warn("Получена пустая фраза от GitHub");
        }
    }
}
