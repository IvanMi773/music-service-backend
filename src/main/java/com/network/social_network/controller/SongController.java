package com.network.social_network.controller;

import com.network.social_network.dto.SongDto;
import com.network.social_network.model.Song;
import com.network.social_network.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/song")
public class SongController {

    private final SongService songService;

    public SongController (SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<Song> getAllSongs () {
        return songService.getAll();
    }

    @GetMapping("/{songId}")
    public Song getSongById (@PathVariable Long songId) {
        return songService.getSongById(songId);
    }

    @PostMapping
    public HashMap<String, String> createSong (
            @RequestParam("username") String username,
            @RequestParam("title") String name,
            @RequestParam("file") MultipartFile file,
            @RequestParam("genre") Long genreId
    ) {
        var songDto = new SongDto(username, name, file, genreId);
        songService.createSong(songDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("ok", String.valueOf(HttpStatus.OK));

        return response;
    }

    @PostMapping("/{songId}")
    public HttpStatus updateSong (@PathVariable Long songId, @RequestBody SongDto songDto) {
        songService.updatePost(songId, songDto);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{songId}")
    public HttpStatus deletePost (@PathVariable Long songId) {
        songService.deleteSongById(songId);

        return HttpStatus.OK;
    }
}
