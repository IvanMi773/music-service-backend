package com.network.social_network.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "songs")
@Where(clause = "is_deleted=0")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "playlist_song",
            joinColumns = { @JoinColumn(name = "song_id") },
            inverseJoinColumns = { @JoinColumn(name = "playlist_id") }
    )
    private Set<Playlist> playlists;

    @OneToMany(mappedBy = "song")
    private List<Comment> comments;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "fileId")
    private SongFile file;

    @ManyToOne
    private Genre genre;

    private String cover;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "song_likes",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likes = new HashSet<>();

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    private LocalDateTime createdAt;

    public Song(
            String name,
            SongFile file,
            Genre genre,
            String cover,
            Boolean isDeleted,
            LocalDateTime createdAt
    ) {
        this.name = name;
        this.file = file;
        this.cover = cover;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        playlists = new HashSet<>();

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

    public Set<User> getLikes () {
        return likes;
    }

    public void setLikes (Set<User> likes) {
        this.likes = likes;
    }

    public Set<Playlist> getPlaylists () {
        return playlists;
    }

    public void setPlaylists (Set<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist (Playlist playlist) {
        this.playlists.add(playlist);
        playlist.getSongs().add(this);
    }

    public void removePlaylist (Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.getSongs().remove(this);
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
