package com.network.social_network.repository;

import com.network.social_network.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void getGenreByName () {
        var genre = new Genre("asdfasdf");
        genreRepository.save(genre);

        assertThat(genreRepository.getGenreByName("asdfasdf")).isInstanceOf(Genre.class);
        genreRepository.deleteById(genre.getId());
    }
}