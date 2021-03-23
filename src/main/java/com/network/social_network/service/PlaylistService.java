package com.network.social_network.service;

import com.network.social_network.dto.playlist.PlaylistDto;
import com.network.social_network.dto.playlist.PlaylistResponseDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.PhotoFile;
import com.network.social_network.model.PlayListState;
import com.network.social_network.model.Playlist;
import com.network.social_network.model.Song;
import com.network.social_network.repository.PhotoFileRepository;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final PhotoFileRepository photoFileRepository;
    private final Path root = Paths.get("uploads/playlist_photos");

    public PlaylistService (
            PlaylistRepository playlistRepository,
            UserRepository userRepository,
            PhotoFileRepository photoFileRepository
    ) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.photoFileRepository = photoFileRepository;
    }

    public List<Playlist> getAll () {
        return playlistRepository.findAll();
    }

    public PlaylistResponseDto getPlaylistById (Long playlistId) {
        var playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new CustomException("Playlist with id " + playlistId + " not found", HttpStatus.NOT_FOUND)
        );

        var songs = new ArrayList<SongResponseDto>();
        for (Song s: playlist.getSongs()) {
            var songDto = new SongResponseDto(
                    playlist.getUser().getUsername(),
                    s.getName(),
                    s.getGenre().getName(),
                    s.getLikes(),
                    s.getSongFile().getFileName(),
                    s.getSongFile().getDuration()
            );
            songs.add(songDto);
        }

        var playlistDto = new PlaylistResponseDto(
                playlist.getId(),
                playlist.getName(),
                playlist.getPhoto().getFileName(),
                playlist.getDuration(),
                songs
        );

        return playlistDto;
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
    // or playlist_photos/jfadlskjs.png
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
            filename = filename + "." + file.getContentType().split("/")[1];

            File image = new File(path);
            BufferedImage bufferedImage = ImageIO.read(image);
            var croppedImage = cropImage(bufferedImage, 0, 0, 300, 300);
            File pathFile = new File(path);
            ImageIO.write(croppedImage,"jpg", pathFile);

            PhotoFile model = new PhotoFile(filename, file.getContentType());
            photoFileRepository.save(model);

            return model;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("io err", HttpStatus.MULTI_STATUS);
        }
    }

    public static BufferedImage cropImage (BufferedImage bufferedImage, int x, int y, int width, int height) {
        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        return croppedImage;
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

    public List<PlaylistResponseDto> getPlaylistsByUsername (String username) {

        var user = userRepository.findByUsername(username);
        var playlists = new ArrayList<PlaylistResponseDto>();

        for (Playlist p: user.getPlaylists()) {

            playlists.add(
                    new PlaylistResponseDto(
                            p.getId(),
                            p.getName(),
                            p.getPhoto().getFileName(),
                            p.getDuration(),
                            null
                    )
            );
        }

        return playlists;
    }
}
