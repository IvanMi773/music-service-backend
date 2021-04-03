package com.network.social_network.repository.elasticsearch;

import com.network.social_network.mapping.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SongElasticsearchRepository extends ElasticsearchRepository<Song, Long> {

    Page<Song> findByName (String name, Pageable pageable);
}
