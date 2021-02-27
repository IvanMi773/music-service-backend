package com.network.social_network.dto;

import org.springframework.web.multipart.MultipartFile;

public class SongDto {

    private String username;
    private String name;
    private MultipartFile file;
    private Long genreId;

    public SongDto (String username, String name, MultipartFile file, Long genreId) {
        this.username = username;
        this.name = name;
        this.file = file;
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
}
