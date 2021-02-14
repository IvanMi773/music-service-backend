package com.network.social_network.controller;

import com.network.social_network.dto.SongDto;
import com.network.social_network.model.Song;
import com.network.social_network.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public HttpStatus createSong (@RequestBody SongDto songDto) {
        songService.createSong(songDto);

        return HttpStatus.OK;
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
