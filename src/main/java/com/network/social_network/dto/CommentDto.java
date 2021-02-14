package com.network.social_network.dto;

public class CommentDto {

    private Long userId;
    private Long songId;
    private String text;

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public Long getSongId () {
        return songId;
    }

    public void setSongId (Long songId) {
        this.songId = songId;
    }

    public String getText () {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }
}
