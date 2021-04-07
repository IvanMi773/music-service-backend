package com.network.social_network.dto.playlist;

import com.network.social_network.dto.song.SongResponseDto;

import java.util.List;

public class PlaylistResponseDto {

    private Long id;
    private String title;
    private String photo;
    private List<SongResponseDto> songs;
    private String username;
    private String state;

    public PlaylistResponseDto(
            Long id,
            String title,
            String photo,
            List<SongResponseDto> songs,
            String username,
            String state
    ) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.songs = songs;
        this.username = username;
        this.state = state;
    }

    public List<SongResponseDto> getSongs () {
        return songs;
    }

    public void setSongs (List<SongResponseDto> songs) {
        this.songs = songs;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getPhoto () {
        return photo;
    }

    public void setPhoto (String photo) {
        this.photo = photo;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
