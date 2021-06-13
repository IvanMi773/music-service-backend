package com.network.social_network.service.search;

import com.network.social_network.model.SearchCriteria;
import com.network.social_network.model.Song;
import com.network.social_network.repository.SongRepository;
import com.network.social_network.searchSpecification.SongSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongSearchService {

    private final SongRepository songRepository;

    public SongSearchService (SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> findByName(String songName) {
        var songSpecification = new SongSpecification(new SearchCriteria("name", ":", songName));
        return songRepository.findAll(songSpecification);
    }
}
