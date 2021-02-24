package com.network.social_network.controller;

import com.network.social_network.dto.GenreDto;
import com.network.social_network.model.Genre;
import com.network.social_network.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController (GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public HashMap<String, String> createGenre (@RequestBody GenreDto genreDto) {
        genreService.create(genreDto.getName());
        var response = new HashMap<String, String>();
        response.put("genreId", String.valueOf(genreService.getGenreByName(genreDto.getName()).getId()));

        return response;
    }

    @GetMapping
    public List<Genre> getAllGenres () {
        return genreService.getAllGenres();
    }
}
