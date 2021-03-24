package com.network.social_network.dto.song;

public class SongLikesDto {

    private Long id;
    private Long countOfLikes;
    private Boolean meLiked;

    public SongLikesDto (Long id, Long countOfLikes, Boolean meLiked) {
        this.id = id;
        this.countOfLikes = countOfLikes;
        this.meLiked = meLiked;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Long getCountOfLikes () {
        return countOfLikes;
    }

    public void setCountOfLikes (Long countOfLikes) {
        this.countOfLikes = countOfLikes;
    }

    public Boolean getMeLiked () {
        return meLiked;
    }

    public void setMeLiked (Boolean meLiked) {
        this.meLiked = meLiked;
    }
}
