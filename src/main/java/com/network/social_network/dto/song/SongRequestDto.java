package com.network.social_network.dto.song;

import org.springframework.web.multipart.MultipartFile;

public class SongRequestDto {

    private String username;
    private String name;
    private MultipartFile file;
    private MultipartFile cover;
    private Long genreId;

    public SongRequestDto(String username, String name, MultipartFile file, MultipartFile cover, Long genreId) {
        this.username = username;
        this.name = name;
        this.file = file;
        this.cover = cover;
        this.genreId = genreId;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public MultipartFile getSong () {
        return file;
    }

    public void setSong (MultipartFile file) {
        this.file = file;
    }

    public Long getGenre () {
        return genreId;
    }

    public void setGenre (Long genreId) {
        this.genreId = genreId;
    }

    public MultipartFile getCover() {
        return cover;
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
