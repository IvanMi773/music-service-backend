package com.network.social_network.service;

import com.network.social_network.dto.SongDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Playlist;
import com.network.social_network.model.Song;
import com.network.social_network.model.SongFile;
import com.network.social_network.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final PlaylistRepository playlistRepository;
    private final FilesRepository filesRepository;
    private final GenreRepository genreRepository;

    public SongService (SongRepository songRepository, UserRepository userRepository, PlaylistRepository playlistRepository, FilesRepository filesRepository, GenreRepository genreRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.filesRepository = filesRepository;
        this.genreRepository = genreRepository;
    }

    public List<Song> getAll () {
        return songRepository.findAll();
    }

    public Song getSongById (Long songId) {
        //Todo: correct exception handling
        return songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );
    }

    public void createSong (SongDto songDto) {
        //Todo: calculate duration of song;

        var songFile = saveSong(songDto.getSong());

        var user = userRepository.findByUsername(songDto.getUsername());

        var song = new Song(
                songDto.getName(),
                songFile,
                genreRepository.findById(songDto.getGenre()).orElseThrow(() -> new CustomException("Genre not found", HttpStatus.NOT_FOUND)),
                (long) 23.4
        );
        song.addPlaylist(user.getPlaylists().get(0));

        songRepository.save(song);
    }

    public SongFile saveSong (MultipartFile file) {
        Path root = Paths.get("uploads");

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

    public void updatePost (Long songId, SongDto songDto) {
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
