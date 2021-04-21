package com.network.social_network.repository.elasticsearch;

import com.network.social_network.elasticsearch_models.Song;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SongElasticsearchRepository extends ElasticsearchRepository<Song, Long> {

    List<Song> findByName (String name);
}
