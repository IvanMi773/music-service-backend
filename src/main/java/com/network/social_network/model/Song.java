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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "fileId")
    private SongFile file;

    private String genre;
    private Long likes;

    public Song (
            String name,
            SongFile file,
            String genre,
            Long likes,
            Playlist playlist
    ) {
        this.name = name;
        this.file = file;
        this.genre = genre;
        this.likes = likes;
        this.playlist = playlist;
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
