package com.network.social_network.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "playlist_song",
            joinColumns = { @JoinColumn(name = "song_id") },
            inverseJoinColumns = { @JoinColumn(name = "playlist_id") }
    )
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "song")
    private List<Comment> comments;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "fileId")
    private SongFile file;

    @ManyToOne
    private Genre genre;

    private Long likes;

    public Song (
            String name,
            SongFile file,
            Genre genre,
            Long likes
    ) {
        this.name = name;
        this.file = file;
        this.likes = likes;
        playlists = new ArrayList<>();

        if (genre != null) {
            this.genre = genre;
        } else {
            this.genre = new Genre("none");
        }
    }

    public Song () {}

    public Long getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public SongFile getSongFile () {
        return file;
    }

    public void setSongFile (SongFile file) {
        this.file = file;
    }

    public Genre getGenre () {
        return genre;
    }

    public void setGenre (Genre genre) {
        this.genre = genre;
    }

    public Long getLikes () {
        return likes;
    }

    public void setLikes (Long likes) {
        this.likes = likes;
    }

    public List<Playlist> getPlaylists () {
        return playlists;
    }

    public void setPlaylists (List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist (Playlist playlist) {
        this.playlists.add(playlist);
    }
}
