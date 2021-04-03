package com.network.social_network.service.elasticsearch;

import com.network.social_network.mapping.Song;
import com.network.social_network.repository.elasticsearch.SongElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongElasticsearchService {

    private final SongElasticsearchRepository songElasticsearchRepository;

    public SongElasticsearchService(SongElasticsearchRepository songElasticsearchRepository) {
        this.songElasticsearchRepository = songElasticsearchRepository;
    }

    public void save (Song song) {
        this.songElasticsearchRepository.save(song);
    }

    public List<Song> findByName(String songName) {
        return songElasticsearchRepository.findByName(songName);
    }
}
