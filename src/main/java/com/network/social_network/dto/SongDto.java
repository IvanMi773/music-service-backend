package com.network.social_network.dto;

import org.springframework.web.multipart.MultipartFile;

public class SongDto {

    private Long playlistId;
    private String name;
    private MultipartFile file;
    private Long genreId;

    public SongDto (Long playlistId, String name, MultipartFile file, Long genreId) {
        this.playlistId = playlistId;
        this.name = name;
        this.file = file;
        this.genreId = genreId;
    }

    public Long getPlaylistId () {
        return playlistId;
    }

    public void setPlaylistId (Long playlistId) {
        this.playlistId = playlistId;
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
