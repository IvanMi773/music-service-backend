package com.network.social_network.service;

import com.network.social_network.dto.song.SongLikesDto;
import com.network.social_network.dto.song.SongRequestDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Song;
import com.network.social_network.model.SongFile;
import com.network.social_network.model.User;
import com.network.social_network.repository.*;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final FilesRepository filesRepository;
    private final GenreRepository genreRepository;
    private final PlaylistRepository playlistRepository;
    private final Path root = Paths.get("uploads/songs");
    private final String fileExpansion = ".mp3";

    public SongService (
            SongRepository songRepository,
            UserRepository userRepository,
            FilesRepository filesRepository,
            GenreRepository genreRepository,
            PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.filesRepository = filesRepository;
        this.genreRepository = genreRepository;
        this.playlistRepository = playlistRepository;
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
                        song.getSongFile().getDuration()
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
                            song.getSongFile().getDuration()
                    )
            );
        }

        return songs;
    }

    public void createSong (SongRequestDto songRequestDto) {
        var songFile = saveSong(songRequestDto.getSong());
        var user = userRepository.findByUsername(songRequestDto.getUsername());

        var song = new Song(
                songRequestDto.getName(),
                songFile,
                genreRepository.findById(songRequestDto.getGenre()).orElseThrow(() -> new CustomException("Genre not found", HttpStatus.NOT_FOUND))
        );
        song.addPlaylist(user.getPlaylists().get(0));

        songRepository.save(song);
    }

    public SongFile saveSong (MultipartFile file) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        try {
            String filename = UUID.randomUUID() + fileExpansion;
            String path = root + "/" + filename;
            Files.write(Paths.get(path), file.getBytes());

            File target = new File("D:\\Projects\\music_service\\social_network\\" + path);
            int duration;

            AudioFile audioFile = AudioFileIO.read(target);
            duration = audioFile.getAudioHeader().getTrackLength();

            SongFile model = new SongFile(filename, duration);
            filesRepository.save(model);

            return model;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("Saving song error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateSong (Long songId, SongRequestDto songRequestDto) {
        //Todo: correct update
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

    public SongLikesDto updateLikesOfSong (Long songId, String username) {
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

        return new SongLikesDto(song.getId(), song.getLikes().stream().count(), song.getLikes().contains(user));
    }

    public SongLikesDto getLikesOfSong (Long songId, String username) {
        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );

        var user = userRepository.findByUsername(username);

        return new SongLikesDto(song.getId(), song.getLikes().stream().count(), song.getLikes().contains(user));
    }

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
                        s.getSongFile().getDuration()
                ));
            }
        }

        return songs;
    }
}
