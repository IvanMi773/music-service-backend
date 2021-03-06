package com.network.social_network.dto.song;

public class SongResponseDto {

    private String username;
    private String name;
    private String genre;
    private Long likes;
    private String file;

    public SongResponseDto (String username, String name, String genre, Long likes, String file) {
        this.username = username;
        this.name = name;
        this.genre = genre;
        this.likes = likes;
        this.file = file;
    }

    public SongResponseDto () {
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

    public Long getLikes () {
        return likes;
    }

    public void setLikes (Long likes) {
        this.likes = likes;
    }

    public String getFile () {
        return file;
    }

    public void setFile (String file) {
        this.file = file;
    }
}
