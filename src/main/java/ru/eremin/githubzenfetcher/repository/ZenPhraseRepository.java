package ru.eremin.githubzenfetcher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eremin.githubzenfetcher.entity.ZenPhrase;

import java.util.List;
import java.util.Optional;


@Repository
public interface ZenPhraseRepository extends JpaRepository<ZenPhrase, Long> {

    Optional<ZenPhrase> findByPhrase(String phrase);

    List<ZenPhrase> findAllByOrderByPhraseCountDesc();
}
