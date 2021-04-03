package com.network.social_network.controller;

import com.network.social_network.service.elasticsearch.SongElasticsearchService;
import com.network.social_network.service.elasticsearch.UserElasticSearchService;
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

    public SearchController(SongElasticsearchService songElasticsearchService, UserElasticSearchService userElasticSearchService) {
        this.songElasticsearchService = songElasticsearchService;
        this.userElasticSearchService = userElasticSearchService;
    }

    @GetMapping("/{searchQuery}")
    public HashMap search (@PathVariable("searchQuery") String searchQuery) {
        var response = new HashMap();

        response.put("songs", songElasticsearchService.findByName(searchQuery));
        response.put("users", userElasticSearchService.findByUsername(searchQuery));

        return response;
    }
}

