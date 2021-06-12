package com.network.social_network.repository;

import com.network.social_network.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void getSongsByGenreId () {
        var genre = new Genre("test");
        genreRepository.save(genre);

        assertThat(songRepository.getSongsByGenreId(genre.getId())).isNotNull();
        genreRepository.deleteById(genre.getId());
    }
}