package com.network.social_network.repository;

import com.network.social_network.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre getGenreByName (String name);
}
