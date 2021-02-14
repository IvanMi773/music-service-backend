package com.network.social_network.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @OneToMany(mappedBy = "song")
    private List<Comment> comments;

    private String name;
    private Double duration;
    private String song;
    private String genre;
    private Long likes;

    public Song (
            String name,
            Double duration,
            String song,
            String genre,
            Long likes
    ) {
        this.name = name;
        this.duration = duration;
        this.song = song;
        this.genre = genre;
        this.likes = likes;
    }

    public Long getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Double getDuration () {
        return duration;
    }

    public void setDuration (Double duration) {
        this.duration = duration;
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
