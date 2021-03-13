package com.network.social_network.controller;

import com.network.social_network.dto.PlaylistDto;
import com.network.social_network.model.Playlist;
import com.network.social_network.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
    public HashMap<String, String> createPlaylist (
            @RequestParam("username") String username,
            @RequestParam("name") String name,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("state") Integer state
    ) {
        var playlistDto = new PlaylistDto(username, name, photo, state);
        playlistService.createPlaylist(playlistDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("ok", String.valueOf(HttpStatus.OK));

        return response;
    }

    @PostMapping("/{playlistId}")
    public HashMap<String, String> updatePlaylist (@PathVariable Long playlistId, @RequestBody PlaylistDto songDto) {
        playlistService.updatePlaylist(playlistId, songDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("ok", String.valueOf(HttpStatus.OK));

        return response;
    }

    @DeleteMapping("/{playlistId}")
    public HttpStatus deletePost (@PathVariable Long playlistId) {
        playlistService.deletePlaylistById(playlistId);

        return HttpStatus.OK;
    }
}
