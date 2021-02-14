package com.network.social_network.dto;

public class SongDto {

    private Long playlistId;
    private String name;
    private String song;
    private String genre;
    private Long likes;

    public SongDto (Long playlistId, String name, String song, String genre, Long likes) {
        this.playlistId = playlistId;
        this.name = name;
        this.song = song;
        this.genre = genre;
        this.likes = likes;
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

    public String getSong () {
        return song;
    }

    public void setSong (String song) {
        this.song = song;
    }

    public String getGenre () {
        return genre;
    }

    public void setGenre (String genre) {
        this.genre = genre;
    }

    public Long getLikes () {
        return likes;
    }

    public void setLikes (Long likes) {
        this.likes = likes;
    }
}
