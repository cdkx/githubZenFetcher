package ru.eremin.githubzenfetcher.client;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange(url = "https://api.github.com")
public interface GitHubClient {

    @GetExchange(url = "/zen")
    String getZenPhrase(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-GitHub-Api-Version") String apiVersion,
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader("Accept") String accept
    );
}
