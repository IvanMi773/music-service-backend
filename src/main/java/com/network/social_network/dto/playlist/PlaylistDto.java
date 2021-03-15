package com.network.social_network.dto.playlist;

import org.springframework.web.multipart.MultipartFile;

public class PlaylistDto {

    private String username;
    private String name;
    private MultipartFile photo;
    private Integer state;

    public PlaylistDto (String username, String name, MultipartFile photo, Integer state) {
        this.username = username;
        this.name = name;
        this.photo = photo;
        this.state = state;
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

    public MultipartFile getPhoto () {
        return photo;
    }

    public void setPhoto (MultipartFile photo) {
        this.photo = photo;
    }

    public Integer getState () {
        return state;
    }

    public void setState (Integer state) {
        this.state = state;
    }
}
