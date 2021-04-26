package com.network.social_network.controller;

import com.network.social_network.dto.song.SongRequestDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.service.SongService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    public SongController(SongService songService) {
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

        try {
            String file = "uploads/songs/" + songName;

            long length = new File(file).length();

            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentLength(length);
            httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found", exc);
        }
    }

    @GetMapping("/cover/{title}")
    public ResponseEntity getCover (@PathVariable String title) throws FileNotFoundException {
        try {
            String file = "uploads/covers/" + title;

            long length = new File(file).length();

            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentLength(length);
            httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cover not found", exc);
        }
    }

    @GetMapping("/s")
    public List<SongResponseDto> getSubscriptionsSongs (@AuthenticationPrincipal org.springframework.security.core.userdetails.User userdetails) {
        return songService.getSubscriptionsSongs(userdetails.getUsername());
    }

    @PostMapping
    public HashMap<String, String> createSong (
            @AuthenticationPrincipal User user,
            @RequestParam("title") String name,
            @RequestParam("file") MultipartFile file,
            @RequestParam("cover") MultipartFile cover,
            @RequestParam("genre") Long genreId
    ) {
        try {
            var songDto = new SongRequestDto(user.getUsername(), name, file, cover, genreId);
            songService.createSong(songDto);

            HashMap<String, String> response = new HashMap<>();
            response.put("ok", String.valueOf(HttpStatus.OK));

            return response;
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error when upload song", exc);
        }
    }

    @PostMapping("/{songId}/p/{playlistId}")
    public HttpStatus saveSongToPlaylist (@PathVariable Long songId, @PathVariable Long playlistId) {
        songService.saveSongToPlaylist(songId, playlistId);

        return HttpStatus.OK;
    }

    @PutMapping("/updateLikes/{songId}")
    public SongResponseDto updateLikesOfSong (@PathVariable Long songId, @RequestBody String username) {
        return songService.updateLikesOfSong(songId, username);
    }

    @DeleteMapping("/{songId}")
    public HttpStatus deleteSong (@PathVariable Long songId) {
        songService.deleteSongById(songId);

        return HttpStatus.OK;
    }
}

