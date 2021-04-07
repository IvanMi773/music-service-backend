package com.network.social_network.service.elasticsearch;

import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.mapping.Song;
import com.network.social_network.repository.SongRepository;
import com.network.social_network.repository.elasticsearch.SongElasticsearchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongElasticsearchService {

    private final SongElasticsearchRepository songElasticsearchRepository;
    private final SongRepository songRepository;

    public SongElasticsearchService(SongElasticsearchRepository songElasticsearchRepository, SongRepository songRepository) {
        this.songElasticsearchRepository = songElasticsearchRepository;
        this.songRepository = songRepository;
    }

    public void removeAll () {
        this.songElasticsearchRepository.deleteAll();
    }

    public void saveAll (List<com.network.social_network.model.Song> songs) {
        for ( var s : songs) {
            songElasticsearchRepository.save(new com.network.social_network.mapping.Song(
                    s.getId(),
                    s.getName()
            ));
        }
    }

    public void save (Song song) {
        this.songElasticsearchRepository.save(song);
    }

    public ArrayList<SongResponseDto> findByName(String songName) {
        var songResponseDtos = new ArrayList<SongResponseDto>();

        for (var s : songElasticsearchRepository.findByName(songName)) {
            var song = songRepository.findById(s.getId()).orElseThrow(
                    () -> new CustomException("Song not found", HttpStatus.NOT_FOUND)
            );
            songResponseDtos.add(new SongResponseDto(
                    song.getId(),
                    song.getPlaylists().iterator().next().getUser().getUsername(),
                    song.getName(),
                    song.getGenre().getName(),
                    song.getSongFile().getFileName(),
                    song.getSongFile().getDuration(),
                    song.getLikes(),
                    song.getCover()
            ));
        }

        return songResponseDtos;
    }
}
