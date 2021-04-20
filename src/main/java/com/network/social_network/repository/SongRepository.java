package com.network.social_network.repository;

import com.network.social_network.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> getSongsByGenreId(Long genreId);
}
