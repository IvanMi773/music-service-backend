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
    private Double duration;

    @ManyToMany(mappedBy = "playlists")
    private List<Song> songs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_file_id", referencedColumnName = "fileId")
    private PhotoFile photoFile;

    private PlayListState state;

    public Playlist (
            User user,
            String name,
            Double duration,
            PhotoFile photoFile,
            PlayListState state
    ) {
        this.user = user;
        this.name = name;
        this.duration = duration;
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

    public PhotoFile getPhoto () {
        return photoFile;
    }

    public void setPhoto (PhotoFile photoFile) {
        this.photoFile = photoFile;
    }

    public PlayListState getState () {
        return state;
    }

    public void setState (PlayListState state) {
        this.state = state;
    }
}
