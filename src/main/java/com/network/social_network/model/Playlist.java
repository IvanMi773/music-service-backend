package com.network.social_network.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "playlists")
@Where(clause = "is_deleted=0")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    @ManyToMany(mappedBy = "playlists")
    private Set<Song> songs = new HashSet<>();

    private String photoFile;

    private PlayListState state;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Playlist(
            User user,
            String name,
            String photoFile,
            PlayListState state,
            boolean isDeleted
    ) {
        this.user = user;
        this.name = name;
        this.photoFile = photoFile;
        this.state = state;
        this.isDeleted = isDeleted;
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

    public Set<Song> getSongs () {
        return songs;
    }

    public void setSongs (Set<Song> songs) {
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

    public void addSong (Song song) {
        this.songs.add(song);
        song.getPlaylists().add(this);
    }

    public void removeSong (Song song) {
        this.songs.remove(song);
        song.getPlaylists().remove(this);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
