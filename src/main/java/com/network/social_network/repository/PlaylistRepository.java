package com.network.social_network.repository;

import com.network.social_network.model.Playlist;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

//    @Query("SELECT * FROM Playlist p WHERE p.state = PUBLIC")
//    List<Playlist> findAllPublic();
}
