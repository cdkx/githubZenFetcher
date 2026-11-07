package ru.eremin.githubzenfetcher.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "zen_phrases")
public class ZenPhrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phrase", nullable = false, unique = true, length = 500)
    private String phrase;


    @Column(name = "phrase_count", nullable = false)
    private Long phraseCount = 1L;

    public ZenPhrase() {
    }

    public ZenPhrase(String phrase) {
        this.phrase = phrase;
        this.phraseCount = 1L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Long getPhraseCount() {
        return phraseCount;
    }

    public void setPhraseCount(Long phraseCount) {
        this.phraseCount = phraseCount;
    }
}
