package com.network.social_network.service;

import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Genre;
import com.network.social_network.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService (GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void create (String name) {
        var genres = genreRepository.getAllGenresByName(name);
        if (genres.isEmpty()) {
            var genre = new Genre(name);
            genreRepository.save(genre);
        } else {
            throw new CustomException("Genre " + name + " is already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public Genre getGenreByName (String name) {
        return genreRepository.getGenreByName(name);
    }

    public Genre getGenreById (Long id) {
        return genreRepository.findById(id).orElseThrow(
                () -> new CustomException("Genre with id " + id + " not found", HttpStatus.OK)
        );
    }

    public List<Genre> getAllGenres () {
        return genreRepository.findAll();
    }
}
