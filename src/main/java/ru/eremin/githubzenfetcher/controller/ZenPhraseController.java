package ru.eremin.githubzenfetcher.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eremin.githubzenfetcher.dto.ZenPhraseResponse;
import ru.eremin.githubzenfetcher.service.ZenPhraseService;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class ZenPhraseController {
    private final ZenPhraseService service;

    public ZenPhraseController(ZenPhraseService service) {
        this.service = service;
    }

    @GetMapping(value = "/zenPhrases",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ZenPhraseResponse>> getAllPhrases() {
        return ResponseEntity.ok(service.getAllPhrases());
    }
}
