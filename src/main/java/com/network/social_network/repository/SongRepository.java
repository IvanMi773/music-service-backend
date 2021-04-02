package com.network.social_network.repository;

import com.network.social_network.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends ElasticsearchRepository<Song, Long> {

    Page<Song> findByName (String name, Pageable pageable);
}
