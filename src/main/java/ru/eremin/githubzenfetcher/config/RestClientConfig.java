package ru.eremin.githubzenfetcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.registry.ImportHttpServices;
import ru.eremin.githubzenfetcher.client.GitHubClient;

import java.time.Duration;

import static org.springframework.web.service.registry.HttpServiceGroup.ClientType.REST_CLIENT;


@Configuration
@EnableRetry
@ImportHttpServices(clientType = REST_CLIENT, value = GitHubClient.class)
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(10));

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }
}
