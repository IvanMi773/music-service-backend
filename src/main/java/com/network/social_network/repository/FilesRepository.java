package com.network.social_network.repository;

import com.network.social_network.model.SongFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<SongFile, Long> {
}
