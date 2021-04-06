package com.network.social_network.service;

import com.network.social_network.dto.song.SongRequestDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Song;
import com.network.social_network.model.User;
import com.network.social_network.repository.*;
import com.network.social_network.repository.SongRepository;
import com.network.social_network.service.elasticsearch.SongElasticsearchService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final FileUploadService fileUploadService;
    private final SongElasticsearchService songElasticsearchService;

    public SongService(
            SongRepository songRepository,
            UserRepository userRepository,
            GenreRepository genreRepository,
            FileUploadService fileUploadService,
            SongElasticsearchService songElasticsearchService
    ) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.fileUploadService = fileUploadService;
        this.songElasticsearchService = songElasticsearchService;
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
                var songDto = new SongResponseDto(
                        song.getId(),
                        user.getUsername(),
                        song.getName(),
                        song.getGenre().getName(),
                        song.getSongFile().getFileName(),
                        song.getSongFile().getDuration(),
                        song.getLikes()
                );
                songs.add(songDto);
            }
        }

        return songs;
    }

    public ArrayList<SongResponseDto> getSongsByUsername (String username) {
        var user = userRepository.findByUsername(username);
        var uploadPlaylist = user.getPlaylists().get(0);
        var songs = new ArrayList<SongResponseDto>();

        for (Song song : uploadPlaylist.getSongs()) {
            songs.add(
                    new SongResponseDto(
                            song.getId(),
                            username,
                            song.getName(),
                            song.getGenre().getName(),
                            song.getSongFile().getFileName(),
                            song.getSongFile().getDuration(),
                            song.getLikes()
                    )
            );
        }

        return songs;
    }

    public void createSong (SongRequestDto songRequestDto) {
        var songFile = fileUploadService.saveSong(songRequestDto.getSong());
        var user = userRepository.findByUsername(songRequestDto.getUsername());

        var song = new Song(
                songRequestDto.getName(),
                songFile,
                genreRepository.findById(songRequestDto.getGenre()).orElseThrow(() -> new CustomException("Genre not found", HttpStatus.NOT_FOUND))
        );
        song.addPlaylist(user.getPlaylists().get(0));

        song = songRepository.save(song);

        songElasticsearchService.save(new com.network.social_network.mapping.Song(
                song.getId(),
                song.getName()
        ));
    }

    public void updateSong (Long songId, SongRequestDto songRequestDto) {
        //Todo: correct update
        //Todo: створювати неіснуючі жанри на бекенді (перевіряти чи жанр існує і якщо ні - створювати)
        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );

//        song.setName(songDto.getName());
//        song.setSong(songDto.getSong());
//        song.setGenre(genreRepository.findById(songDto.getGenre()).orElseThrow(() -> new CustomException("Genre not found", HttpStatus.NOT_FOUND)));
//        song.setLikes(songDto.getLikes());

        songRepository.save(song);
    }

    public void deleteSongById (Long songId) {
        songRepository.deleteById(songId);
    }

    public SongResponseDto updateLikesOfSong (Long songId, String username) {
        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );

        var user = userRepository.findByUsername(username);

        if (song.getLikes().contains(user)) {
            song.getLikes().remove(user);
            song.removeFromPlaylist(user.getPlaylists().get(1));
        } else {
            song.getLikes().add(user);
            song.addPlaylist(user.getPlaylists().get(1));
        }

        songRepository.save(song);

        return new SongResponseDto(
                song.getId(),
                username,
                song.getName(),
                song.getGenre().getName(),
                song.getSongFile().getFileName(),
                song.getSongFile().getDuration(),
                song.getLikes()
        );
    }

//    public SongLikesDto getLikesOfSong (Long songId, String username) {
//        var song = songRepository.findById(songId).orElseThrow(
//                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
//        );
//
//        var user = userRepository.findByUsername(username);
//
//        return new SongLikesDto(song.getId(), (long) song.getLikes().size(), song.getLikes().contains(user));
//    }

    public List<SongResponseDto> getSubscriptionsSongs (String username) {

        var user = userRepository.findByUsername(username);
        var songs = new ArrayList<SongResponseDto>();

        for (User u : user.getSubscriptions()) {
            for (Song s : u.getPlaylists().get(0).getSongs()) {
                songs.add(new SongResponseDto(
                        s.getId(),
                        u.getUsername(),
                        s.getName(),
                        s.getGenre().getName(),
                        s.getSongFile().getFileName(),
                        s.getSongFile().getDuration(),
                        s.getLikes())
                );
            }
        }

        return songs;
    }

    public void saveSongToHistory (Long songId, String username) {
        var user = userRepository.findByUsername(username);
        var song = songRepository.findById(songId).orElseThrow(() -> new CustomException("Song not found", HttpStatus.NOT_FOUND));
        song.addPlaylist(user.getPlaylists().get(2)); // History playlist
        songRepository.save(song);
    }
}
