package com.network.social_network.service;

import com.network.social_network.dto.SongDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Song;
import com.network.social_network.repository.SongRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService (SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getAll () {
        return songRepository.findAll();
    }

    public Song getSongById (Long songId) {
        return songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );
    }

    public void createSong (SongDto songDto) {
        //Todo: calculate duration of song;
        var song = new Song(
                songDto.getName(),
                32.3,
                songDto.getSong(),
                songDto.getGenre(),
                songDto.getLikes()
        );

        songRepository.save(song);
    }

    public void updatePost (Long songId, SongDto songDto) {
        //Todo: update more fields
        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );

        song.setName(songDto.getName());
        song.setSong(songDto.getSong());
        song.setGenre(songDto.getGenre());
        song.setLikes(songDto.getLikes());

        songRepository.save(song);
    }

    public void deleteSongById (Long songId) {
        songRepository.deleteById(songId);
    }
}
