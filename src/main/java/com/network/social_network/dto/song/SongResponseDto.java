package com.network.social_network.dto.song;

import com.network.social_network.model.User;

import java.util.Set;

public class SongResponseDto {

    private Long id;
    private String username;
    private String name;
    private String genre;
    private String file;
    private Integer duration;
    private Set<User> likes;
    private String cover;

    public SongResponseDto(
            Long id,
            String username,
            String name,
            String genre,
            String file,
            Integer duration,
            Set<User> likes,
            String cover
    ) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.genre = genre;
        this.file = file;
        this.duration = duration;
        this.likes = likes;
        this.cover = cover;
    }

    public SongResponseDto () {
    }

    public Long getId () {
        return id;
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

    public String getGenre () {
        return genre;
    }

    public void setGenre (String genre) {
        this.genre = genre;
    }

    public String getFile () {
        return file;
    }

    public void setFile (String file) {
        this.file = file;
    }

    public Integer getDuration () {
        return duration;
    }

    public void setDuration (Integer duration) {
        this.duration = duration;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
