package com.network.social_network.controller;

import com.network.social_network.dto.PlaylistDto;
import com.network.social_network.model.Playlist;
import com.network.social_network.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController (PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists () {
        return playlistService.getAll();
    }

    @GetMapping("/{playlistId}")
    public Playlist getPlaylistById (@PathVariable Long playlistId) {
        return playlistService.getPlaylistById(playlistId);
    }

    @PostMapping
    public HttpStatus createPlaylist (@RequestBody PlaylistDto playlistDto) {
        playlistService.createPlaylist(playlistDto);

        return HttpStatus.OK;
    }

    @PostMapping("/{playlistId}")
    public HttpStatus updatePlaylist (@PathVariable Long playlistId, @RequestBody PlaylistDto songDto) {
        playlistService.updatePlaylist(playlistId, songDto);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{playlistId}")
    public HttpStatus deletePost (@PathVariable Long playlistId) {
        playlistService.deletePlaylistById(playlistId);

        return HttpStatus.OK;
    }
}
