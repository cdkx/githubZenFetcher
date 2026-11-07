package ru.eremin.githubzenfetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GithubZenFetcherApplication {
    static void main(String[] args) {
        SpringApplication.run(GithubZenFetcherApplication.class, args);
    }
}
