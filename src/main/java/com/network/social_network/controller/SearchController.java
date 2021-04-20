package com.network.social_network.controller;

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

import java.util.HashMap;

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

    @GetMapping("/{searchQuery}")
    public HashMap search (@PathVariable("searchQuery") String searchQuery) {
        var response = new HashMap();

        response.put("songs", songElasticsearchService.findByName(searchQuery));
        response.put("users", userElasticSearchService.findByUsername(searchQuery));
        response.put("genres", songService.getSongsByGenreName(searchQuery));

        return response;
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

