package com.network.social_network.controller;

import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.model.Song;
import com.network.social_network.model.User;
import com.network.social_network.service.SongService;
import com.network.social_network.service.search.SongSearchService;
import com.network.social_network.service.search.UserSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SongSearchService songSearchService;
    private final UserSearchService userSearchService;
    private final SongService songService;

    public SearchController(
            SongSearchService songSearchService,
            UserSearchService userSearchService,
            SongService songService
    ) {
        this.songSearchService = songSearchService;
        this.userSearchService = userSearchService;
        this.songService = songService;
    }

    @GetMapping("/song-title/{searchQuery}")
    public List<Song> searchSongsByTitle (@PathVariable("searchQuery") String searchQuery) {
        return songSearchService.findByName(searchQuery);
    }

    @GetMapping("/song-genre/{searchQuery}")
    public List<SongResponseDto> searchSongsByGenre (@PathVariable("searchQuery") String searchQuery) {
        return songService.getSongsByGenreName(searchQuery);
    }

    @GetMapping("/user-username/{searchQuery}")
    public List<User> searchUsersByUsername (@PathVariable("searchQuery") String searchQuery) {
        return userSearchService.searchByUsername(searchQuery);
    }
}

