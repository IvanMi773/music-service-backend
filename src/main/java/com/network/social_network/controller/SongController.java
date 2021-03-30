package com.network.social_network.controller;

import com.network.social_network.dto.song.SongLikesDto;
import com.network.social_network.dto.song.SongRequestDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.service.SongService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    public ArrayList<SongResponseDto> getAllSongs () {
        return songService.getAll();
    }

    @GetMapping("/user/{username}")
    public ArrayList<SongResponseDto> getAllSongsByUsername (@PathVariable String username) {
        return songService.getSongsByUsername(username);
    }

    @RequestMapping(
            value = "/{songName}",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_OCTET_STREAM_VALUE
            }
    )
    public ResponseEntity getSongByName(HttpServletRequest request, HttpServletResponse response, @PathVariable String songName) throws FileNotFoundException {

        String file = "uploads/songs/" + songName;

        long length = new File(file).length();

        InputStreamResource inputStreamResource = new InputStreamResource( new FileInputStream(file));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/s")
    public List<SongResponseDto> getSubscribersSongs (@AuthenticationPrincipal org.springframework.security.core.userdetails.User userdetails) {
        return songService.getSubscriptionsSongs(userdetails.getUsername());
    }

    @PostMapping
    public HashMap<String, String> createSong (
            @RequestParam("username") String username,
            @RequestParam("title") String name,
            @RequestParam("file") MultipartFile file,
            @RequestParam("genre") Long genreId
    ) {
        var songDto = new SongRequestDto(username, name, file, genreId);
        songService.createSong(songDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("ok", String.valueOf(HttpStatus.OK));

        return response;
    }

    @PostMapping("/{songId}")
    public HttpStatus updateSong (@PathVariable Long songId, @RequestBody SongRequestDto songRequestDto) {
        songService.updateSong(songId, songRequestDto);

        return HttpStatus.OK;
    }

    @GetMapping("/likes/{songId}/{username}")
    public SongLikesDto getLikesOfSong (@PathVariable Long songId, @PathVariable String username) {
        return songService.getLikesOfSong(songId, username);
    }

    @PutMapping("/updateLikes/{songId}")
    public SongLikesDto updateLikesOfSong (@PathVariable Long songId, @RequestBody String username) {
        return songService.updateLikesOfSong(songId, username);
    }

    @DeleteMapping("/{songId}")
    public HttpStatus deletePost (@PathVariable Long songId) {
        songService.deleteSongById(songId);

        return HttpStatus.OK;
    }
}
