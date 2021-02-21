package com.network.social_network.service;

import com.network.social_network.dto.PlaylistDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.PlayListState;
import com.network.social_network.model.Playlist;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistService (PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
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
        var playlist = new Playlist(
                userRepository
                        .findById(playlistDto.getUserId())
                        .orElseThrow(
                                () -> new CustomException("User with id " + playlistDto.getUserId() + " not found", HttpStatus.NOT_FOUND)
                        ),
                playlistDto.getName(),
                0.0,
                playlistDto.getPhoto(),
                playlistDto.getState() == 0 ? PlayListState.PRIVATE : PlayListState.PUBLIC
        );

        playlistRepository.save(playlist);
    }

    public void updatePlaylist (Long playlistId, PlaylistDto playlistDto) {
        var playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new CustomException("Playlist with id " + playlistId + " not found", HttpStatus.NOT_FOUND)
        );

        playlist.setName(playlistDto.getName());
        playlist.setPhoto(playlistDto.getPhoto());
        playlist.setState(playlistDto.getState() == 0 ? PlayListState.PRIVATE : PlayListState.PUBLIC);

        playlistRepository.save(playlist);
    }
}
