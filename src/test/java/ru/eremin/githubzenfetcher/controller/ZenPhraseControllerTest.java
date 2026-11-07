package ru.eremin.githubzenfetcher.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.eremin.githubzenfetcher.dto.ZenPhraseResponse;
import ru.eremin.githubzenfetcher.service.ZenPhraseService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ZenPhraseController.class)
class ZenPhraseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ZenPhraseService service;

    @Test
    void getAllPhrases_returnsList() throws Exception {
        when(service.getAllPhrases()).thenReturn(List.of(
                new ZenPhraseResponse("phrase one", 10L),
                new ZenPhraseResponse("phrase two", 5L)
        ));

        mockMvc.perform(get("/api/v1/zenPhrases"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].phrase").value("phrase one"))
                .andExpect(jsonPath("$[0].phraseCount").value(10))
                .andExpect(jsonPath("$[1].phrase").value("phrase two"))
                .andExpect(jsonPath("$[1].phraseCount").value(5));
    }

    @Test
    void getAllPhrases_emptyList_returnsEmptyArray() throws Exception {
        when(service.getAllPhrases()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/zenPhrases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
