package com.network.social_network.repository;

import com.network.social_network.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<List<Song>> getSongsByGenreId(Long genreId);
}
