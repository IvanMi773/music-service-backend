package com.network.social_network.service;

import com.network.social_network.dto.playlist.PlaylistDto;
import com.network.social_network.dto.playlist.PlaylistResponseDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.PlayListState;
import com.network.social_network.model.Playlist;
import com.network.social_network.model.Song;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public PlaylistService (
            PlaylistRepository playlistRepository,
            UserRepository userRepository,
            FileUploadService fileUploadService) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
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
                    s.getId(),
                    playlist.getUser().getUsername(),
                    s.getName(),
                    s.getGenre().getName(),
                    s.getSongFile().getFileName(),
                    s.getSongFile().getDuration()
            );
            songs.add(songDto);
        }

        var playlistDto = new PlaylistResponseDto(
                playlist.getId(),
                playlist.getName(),
                playlist.getPhoto(),
                songs
        );

        return playlistDto;
    }

    public void deletePlaylistById (Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public void createPlaylist (PlaylistDto playlistDto) {

        var photoFile = fileUploadService.savePlaylistPhoto(playlistDto.getPhoto());

        //Todo: throw exception if user not found
        var playlist = new Playlist(
                userRepository.findByUsername(playlistDto.getUsername()),
                playlistDto.getName(),
                photoFile,
                playlistDto.getState() == 0 ? PlayListState.PRIVATE : PlayListState.PUBLIC
        );

        playlistRepository.save(playlist);
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
                            p.getPhoto(),
                            null
                    )
            );
        }

        return playlists;
    }
}
