package com.network.social_network.service;

import com.network.social_network.dto.song.SongRequestDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Song;
import com.network.social_network.model.SongFile;
import com.network.social_network.model.User;
import com.network.social_network.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final FilesRepository filesRepository;
    private final GenreRepository genreRepository;
    private final Path root = Paths.get("uploads/songs");

    public SongService (SongRepository songRepository, UserRepository userRepository, PlaylistRepository playlistRepository, FilesRepository filesRepository, GenreRepository genreRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.filesRepository = filesRepository;
        this.genreRepository = genreRepository;
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
                        user.getUsername(),
                        song.getName(),
                        song.getGenre().getName(),
                        song.getLikes(),
                        song.getSongFile().getFileName() + ".mpeg"
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

        for (Song song: uploadPlaylist.getSongs()) {
            songs.add(
                new SongResponseDto(
                    username,
                    song.getName(),
                    song.getGenre().getName(),
                    song.getLikes(),
                    song.getSongFile().getFileName() + ".mpeg"
                )
            );
        }

        return songs;
    }

    public void createSong (SongRequestDto songRequestDto) {
        //Todo: calculate duration of song;

        var songFile = saveSong(songRequestDto.getSong());
        var user = userRepository.findByUsername(songRequestDto.getUsername());

        var song = new Song(
                songRequestDto.getName(),
                songFile,
                genreRepository.findById(songRequestDto.getGenre()).orElseThrow(() -> new CustomException("Genre not found", HttpStatus.NOT_FOUND)),
                (long) 23.4
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
            String filename = String.valueOf(UUID.randomUUID());
            String path = root + "/" + filename + "." + file.getContentType().split("/")[1];
            Files.write(Paths.get(path), file.getBytes());

//            path = path.replace("/", "\\");
//            File target = new File("D:\\Projects\\music_service\\social_network\\" + path);
//            AudioFile af = AudioFileIO.read(target);
//            AudioHeader ah = af.getAudioHeader();
//            System.out.println( ah.getTrackLength());

            SongFile model = new SongFile(filename, file.getContentType());
            filesRepository.save(model);

            return model;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("io err", HttpStatus.MULTI_STATUS);
        }
    }

    public void updatePost (Long songId, SongRequestDto songRequestDto) {
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
}
