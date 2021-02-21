package com.network.social_network.service;

import com.network.social_network.dto.SongDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Song;
import com.network.social_network.model.SongFile;
import com.network.social_network.repository.FilesRepository;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.SongRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;
    private final FilesRepository filesRepository;

    public SongService (SongRepository songRepository, PlaylistRepository playlistRepository, FilesRepository filesRepository) {
        this.songRepository = songRepository;
        this.playlistRepository = playlistRepository;
        this.filesRepository = filesRepository;
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

        var songFile = saveSong(songDto.getSong());

        var song = new Song(
                songDto.getName(),
                songFile,
                songDto.getGenre(),
                (long) 23.4,
                playlistRepository.findById(songDto.getPlaylistId()).orElseThrow(
                        () -> new CustomException("Not found", HttpStatus.NOT_FOUND)
                )
        );

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

            SongFile model = new SongFile(filename, file.getContentType());
            filesRepository.save(model);

            return model;
        } catch (IOException e) {
            //Todo: change exception
            throw new CustomException("err", HttpStatus.MULTI_STATUS);
        }
    }

    public void updatePost (Long songId, SongDto songDto) {
        //Todo: correct update
        var song = songRepository.findById(songId).orElseThrow(
                () -> new CustomException("Song with id " + songId + " not found", HttpStatus.NOT_FOUND)
        );

        song.setName(songDto.getName());
//        song.setSong(songDto.getSong());
        song.setGenre(songDto.getGenre());
//        song.setLikes(songDto.getLikes());

        songRepository.save(song);
    }

    public void deleteSongById (Long songId) {
        songRepository.deleteById(songId);
    }
}
