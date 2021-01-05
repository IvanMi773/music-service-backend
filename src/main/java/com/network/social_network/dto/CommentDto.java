package com.network.social_network.dto;

public class CommentDto {

    private Long userId;
    private Long postId;
    private String text;

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public Long getPostId () {
        return postId;
    }

    public void setPostId (Long postId) {
        this.postId = postId;
    }

    public String getText () {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }
}
