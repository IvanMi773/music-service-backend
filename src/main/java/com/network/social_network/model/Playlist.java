package com.network.social_network.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @ManyToMany(mappedBy = "playlists", cascade = CascadeType.ALL)
    private List<Song> songs;

    private String photoFile;

    private PlayListState state;

    public Playlist (
            User user,
            String name,
            String photoFile,
            PlayListState state
    ) {
        this.user = user;
        this.name = name;
        this.photoFile = photoFile;
        this.state = state;
    }

    public Playlist () {}

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

    public List<Song> getSongs () {
        return songs;
    }

    public void setSongs (List<Song> songs) {
        this.songs = songs;
    }

    public String getPhoto () {
        return photoFile;
    }

    public void setPhoto (String photoFile) {
        this.photoFile = photoFile;
    }

    public PlayListState getState () {
        return state;
    }

    public void setState (PlayListState state) {
        this.state = state;
    }
}
