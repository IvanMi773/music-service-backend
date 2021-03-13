package com.network.social_network.service;

import com.network.social_network.dto.PlaylistDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.PhotoFile;
import com.network.social_network.model.PlayListState;
import com.network.social_network.model.Playlist;
import com.network.social_network.model.SongFile;
import com.network.social_network.repository.PhotoFileRepository;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
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
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final PhotoFileRepository photoFileRepository;
    private final Path root = Paths.get("uploads/playlist_photos");

    public PlaylistService (PlaylistRepository playlistRepository, UserRepository userRepository, PhotoFileRepository photoFileRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.photoFileRepository = photoFileRepository;
    }

    public List<Playlist> getAll () {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistById (Long playlistId) {
        return playlistRepository.findById(playlistId).orElseThrow(
                () -> new CustomException("Playlist with id " + playlistId + " not found", HttpStatus.NOT_FOUND)
        );
    }

    public void deletePlaylistById (Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public void createPlaylist (PlaylistDto playlistDto) {

        var photoFile = savePhoto(playlistDto.getPhoto());

        //Todo: throw exception if user not found
        var playlist = new Playlist(
                userRepository.findByUsername(playlistDto.getUsername()),
                playlistDto.getName(),
                0.0,
                photoFile,
                playlistDto.getState() == 0 ? PlayListState.PRIVATE : PlayListState.PUBLIC
        );

        playlistRepository.save(playlist);
    }

    //Todo: move to a separate class
    //Todo: create common table for all files. save song name something like songs/asdfsdjflkfj.mpeg
    // or playlist_photos/jfadlskjs.mpeg
    public PhotoFile savePhoto (MultipartFile file) {
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

            PhotoFile model = new PhotoFile(filename, file.getContentType());
            photoFileRepository.save(model);

            return model;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("io err", HttpStatus.MULTI_STATUS);
        }
    }

    public void updatePlaylist (Long playlistId, PlaylistDto playlistDto) {
        var playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new CustomException("Playlist with id " + playlistId + " not found", HttpStatus.NOT_FOUND)
        );

        playlist.setName(playlistDto.getName());
//        playlist.setPhoto(playlistDto.getPhoto());
        playlist.setState(playlistDto.getState() == 0 ? PlayListState.PRIVATE : PlayListState.PUBLIC);

        playlistRepository.save(playlist);
    }
}
