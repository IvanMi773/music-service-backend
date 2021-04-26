package com.network.social_network.controller;

import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.repository.SongRepository;
import com.network.social_network.repository.UserRepository;
import com.network.social_network.service.SongService;
import com.network.social_network.service.elasticsearch.SongElasticsearchService;
import com.network.social_network.service.elasticsearch.UserElasticSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SongElasticsearchService songElasticsearchService;
    private final UserElasticSearchService userElasticSearchService;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final SongService songService;

    public SearchController(
            SongElasticsearchService songElasticsearchService,
            UserElasticSearchService userElasticSearchService,
            SongRepository songRepository,
            UserRepository userRepository,
            SongService songService
    ) {
        this.songElasticsearchService = songElasticsearchService;
        this.userElasticSearchService = userElasticSearchService;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.songService = songService;
    }

    @GetMapping("/song-title/{searchQuery}")
    public ArrayList<SongResponseDto> searchSongsByTitle (@PathVariable("searchQuery") String searchQuery) {
        return songElasticsearchService.findByName(searchQuery);
    }

    @GetMapping("/song-genre/{searchQuery}")
    public List<SongResponseDto> searchSongsByGenre (@PathVariable("searchQuery") String searchQuery) {
        return songService.getSongsByGenreName(searchQuery);
    }

    @GetMapping("/user-username/{searchQuery}")
    public ArrayList<UserProfileDto> searchUsersByUsername (@PathVariable("searchQuery") String searchQuery) {
        return userElasticSearchService.findByUsername(searchQuery);
    }

    @GetMapping("/update")
    public HttpStatus update () {
        songElasticsearchService.removeAll();
        songElasticsearchService.saveAll(songRepository.findAll());

        userElasticSearchService.removeAll();
        userElasticSearchService.saveAll(userRepository.findAll());

        return HttpStatus.OK;
    }
}

