package com.network.social_network.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private Double duration;

    @OneToMany(mappedBy = "playlist")
    private List<Song> songs;

    private String photo;
    private PlayListState state;

    public Playlist (
            Long id,
            User user,
            String name,
            Double duration,
            String photo,
            PlayListState state
    ) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.duration = duration;
        this.photo = photo;
        this.state = state;
    }

    public Long getId () {
        return id;
    }

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
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

    public List<Song> getSongs () {
        return songs;
    }

    public void setSongs (List<Song> songs) {
        this.songs = songs;
    }

    public String getPhoto () {
        return photo;
    }

    public void setPhoto (String photo) {
        this.photo = photo;
    }

    public PlayListState getState () {
        return state;
    }

    public void setState (PlayListState state) {
        this.state = state;
    }
}
