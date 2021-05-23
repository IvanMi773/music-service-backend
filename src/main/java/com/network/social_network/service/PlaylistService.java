package com.network.social_network.service;

import com.network.social_network.dto.playlist.PlaylistDto;
import com.network.social_network.dto.playlist.PlaylistResponseDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.mapper.PlaylistMapper;
import com.network.social_network.mapper.SongMapper;
import com.network.social_network.model.PlayListState;
import com.network.social_network.model.Playlist;
import com.network.social_network.model.Song;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    private final SongMapper songMapper;
    private final PlaylistMapper playlistMapper;

    public PlaylistService (
            PlaylistRepository playlistRepository,
            UserRepository userRepository,
            FileUploadService fileUploadService,
            SongMapper songMapper,
            PlaylistMapper playlistMapper
    ) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
        this.songMapper = songMapper;
        this.playlistMapper = playlistMapper;
    }

    public List<PlaylistResponseDto> getAll () {
        var playlistResponseDtoList = new ArrayList<PlaylistResponseDto>();

        for (var playlist : playlistRepository.findAll()) {
            var songs = new ArrayList<SongResponseDto>();
            for (Song s: playlist.getSongs()) {
                songs.add(songMapper.songToSongResponseDto(s));
            }

            playlistResponseDtoList.add(playlistMapper.playlistToPlaylistResponseDto(playlist, songs));
        }
        return playlistResponseDtoList;
    }

    public PlaylistResponseDto getPlaylistById (Long playlistId) {
        var playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new CustomException("Playlist with id " + playlistId + " not found", HttpStatus.NOT_FOUND)
        );

        var songs = new ArrayList<SongResponseDto>();
        for (Song s: playlist.getSongs()) {
            songs.add(songMapper.songToSongResponseDto(s));
        }

        songs.sort(Collections.reverseOrder());

        return playlistMapper.playlistToPlaylistResponseDto(playlist, songs);
    }

    public void deletePlaylistById (Long playlistId) {
        var playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new CustomException("Playlist with id " + playlistId + " not found", HttpStatus.NOT_FOUND)
        );
        playlist.setDeleted(true);
        playlistRepository.save(playlist);
    }

    public void createPlaylist (String username, PlaylistDto playlistDto) {

        var photoFile = fileUploadService.savePlaylistPhoto(playlistDto.getPhoto());

        //Todo: throw exception if user not found
        var playlist = new Playlist(
                userRepository.findByUsername(username),
                playlistDto.getName(),
                photoFile,
                playlistDto.getState() == 0 ? PlayListState.PRIVATE : PlayListState.PUBLIC,
                false);

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

            playlists.add(playlistMapper.playlistToPlaylistResponseDto(p, null));
        }

        return playlists;
    }
}
