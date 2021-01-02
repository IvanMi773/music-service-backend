package com.network.social_network.dto;

public class PostDto {

    private Long userId;
    private String text;
    private String image;

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public String getText () {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }
}
