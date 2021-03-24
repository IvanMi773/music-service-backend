package com.network.social_network.dto.song;

public class SongResponseDto {

    private Long id;
    private String username;
    private String name;
    private String genre;
    private String file;
    private Integer duration;

    public SongResponseDto (
            Long id,
            String username,
            String name,
            String genre,
            String file,
            Integer duration
    ) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.genre = genre;
        this.file = file;
        this.duration = duration;
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
}
