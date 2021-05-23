package com.network.social_network.service;

import com.network.social_network.dto.song.SongRequestDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.mapper.SongMapper;
import com.network.social_network.model.Song;
import com.network.social_network.model.User;
import com.network.social_network.repository.*;
import com.network.social_network.repository.SongRepository;
import com.network.social_network.service.elasticsearch.SongElasticsearchService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final FileUploadService fileUploadService;
    private final SongElasticsearchService songElasticsearchService;
    private final PlaylistRepository playlistRepository;
    private final SongMapper songMapper;

    public SongService (
            SongRepository songRepository,
            UserRepository userRepository,
            GenreRepository genreRepository,
            FileUploadService fileUploadService,
            SongElasticsearchService songElasticsearchService,
            PlaylistRepository playlistRepository,
            SongMapper songMapper
    ) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.fileUploadService = fileUploadService;
        this.songElasticsearchService = songElasticsearchService;
        this.playlistRepository = playlistRepository;
        this.songMapper = songMapper;
    }

    public ArrayList<SongResponseDto> getAll () {
        //Todo: optimise request [just get user playlists]
        //Todo: reorder list from newest to oldest
        var users = userRepository.findAll();
        var songs = new ArrayList<SongResponseDto>();

        for (User user : users) {
            if (user.getPlaylists().size() <= 0) {
                continue;
            }

            var uploadPlaylist = user.getPlaylists().get(0);

            for (Song song : uploadPlaylist.getSongs()) {
                var songDto = songMapper.songToSongResponseDto(song);
                songs.add(songDto);
            }
        }

        songs.sort(Collections.reverseOrder());

        return songs;
    }

    public ArrayList<SongResponseDto> getSongsByUsername (String username) {
        var user = userRepository.findByUsername(username);
        var uploadPlaylist = user.getPlaylists().get(0);
        var songs = new ArrayList<SongResponseDto>();

        for (Song song : uploadPlaylist.getSongs()) {
            songs.add(songMapper.songToSongResponseDto(song));
        }

        songs.sort(Collections.reverseOrder());

        return songs;
    }

    public void createSong (SongRequestDto songRequestDto) {
        var songFile = fileUploadService.saveSong(songRequestDto.getSong());
        var coverFile = fileUploadService.saveSongCover(songRequestDto.getCover());
        var user = userRepository.findByUsername(songRequestDto.getUsername());

        var song = new Song(
                user,
                songRequestDto.getName(),
                songFile,
                genreRepository.findById(songRequestDto.getGenre()).orElseThrow(() -> new CustomException("Genre not found", HttpStatus.NOT_FOUND)),
                coverFile,
                false,
                LocalDateTime.now()
        );
        song.addPlaylist(user.getPlaylists().get(0));

        song = songRepository.save(song);

        songElasticsearchService.save(new com.network.social_network.elasticsearch_models.Song(
                song.getId(),
                song.getName()
        ));
    }

    public void deleteSongById (Long songId) {
        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song not found", HttpStatus.NOT_FOUND)
        );

        song.setDeleted(true);
        songRepository.save(song);
        songElasticsearchService.removeAll();
        songElasticsearchService.saveAll(songRepository.findAll());
    }

    public SongResponseDto updateLikesOfSong (Long songId, String username) {
        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );

        var user = userRepository.findByUsername(username);

        if (song.getLikes().contains(user)) {
            song.getLikes().remove(user);
            song.removePlaylist(user.getPlaylists().get(1));
        } else {
            song.getLikes().add(user);
            song.addPlaylist(user.getPlaylists().get(1));
        }

        songRepository.save(song);

        return songMapper.songToSongResponseDto(song);
    }

    public List<SongResponseDto> getSubscriptionsSongs (String username) {

        var user = userRepository.findByUsername(username);
        var songs = new ArrayList<SongResponseDto>();

        for (User u : user.getSubscriptions()) {
            for (Song s : u.getPlaylists().get(0).getSongs()) {
                songs.add(songMapper.songToSongResponseDto(s));
            }
        }

        songs.sort(Collections.reverseOrder());

        return songs;
    }

    public void saveSongToPlaylist (Long songId, Long playlistId) {
        var playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new CustomException("Playlist not found", HttpStatus.NOT_FOUND)
        );

        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song not found", HttpStatus.NOT_FOUND)
        );
        song.addPlaylist(playlist);
        songRepository.save(song);
    }

    public List<SongResponseDto> getSongsByGenreName(String genreName) {

        var genre = genreRepository.getGenreByName(genreName);
        if (genre == null) {
            throw new CustomException("Genre with name " + genreName + " not found", HttpStatus.NOT_FOUND);
        }
        var songs = songRepository.getSongsByGenreId(genre.getId()).orElseThrow(
                () -> new CustomException("Don't find genre with name " + genreName, HttpStatus.NOT_FOUND)
        );
        var response = new ArrayList<SongResponseDto>();

        for (Song s : songs) {
            response.add(songMapper.songToSongResponseDto(s));
        }

        response.sort(Collections.reverseOrder());
        return response;
    }
}
