package com.network.social_network.controller;

import com.network.social_network.dto.playlist.PlaylistDto;
import com.network.social_network.dto.playlist.PlaylistResponseDto;
import com.network.social_network.model.Playlist;
import com.network.social_network.service.PlaylistService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public List<PlaylistResponseDto> getAllPlaylists () {
        return playlistService.getAll();
    }

    @GetMapping("/{playlistId}")
    public PlaylistResponseDto getPlaylistById (@PathVariable Long playlistId) {
        return playlistService.getPlaylistById(playlistId);
    }

    @PostMapping
    public HashMap<String, String> createPlaylist (
            @AuthenticationPrincipal User user,
            @RequestParam("name") String name,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("state") Integer state
    ) {
        var playlistDto = new PlaylistDto(name, photo, state);
        playlistService.createPlaylist(user.getUsername(), playlistDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("ok", String.valueOf(HttpStatus.OK));

        return response;
    }

    @PostMapping("/{playlistId}")
    public HashMap<String, String> updatePlaylist (
            @PathVariable Long playlistId,
            @RequestBody PlaylistDto playlistDto
    ) {
        playlistService.updatePlaylist(playlistId, playlistDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("ok", String.valueOf(HttpStatus.OK));

        return response;
    }

    @GetMapping("/user/{username}")
    public List<PlaylistResponseDto> getPlaylistsByUsername (@PathVariable String username) {
        List<PlaylistResponseDto> response = playlistService.getPlaylistsByUsername(username);

        return response;
    }

    @GetMapping("/photo/{title}")
    public ResponseEntity getPlaylistPhoto (@PathVariable String title) throws FileNotFoundException {
        String file = "uploads/playlist_photos/" + title;

        long length = new File(file).length();

        InputStreamResource inputStreamResource = new InputStreamResource( new FileInputStream(file));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{playlistId}")
    public HttpStatus deletePlaylist (@PathVariable Long playlistId) {
        playlistService.deletePlaylistById(playlistId);

        return HttpStatus.OK;
    }
}
