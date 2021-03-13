package com.network.social_network.repository;

import com.network.social_network.model.PhotoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoFileRepository extends JpaRepository<PhotoFile, Long> {
}
