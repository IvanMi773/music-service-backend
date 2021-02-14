package com.network.social_network.dto;

public class PlaylistDto {

    private Long userId;
    private String name;
    private String photo;
    private Integer state;

    public PlaylistDto (Long userId, String name, String photo, Integer state) {
        this.userId = userId;
        this.name = name;
        this.photo = photo;
        this.state = state;
    }

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPhoto () {
        return photo;
    }

    public void setPhoto (String photo) {
        this.photo = photo;
    }

    public Integer getState () {
        return state;
    }

    public void setState (Integer state) {
        this.state = state;
    }
}
