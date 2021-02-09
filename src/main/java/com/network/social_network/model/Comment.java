package com.network.social_network.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @NotBlank(message = "Text is required")
    private String text;

    private Instant created_at;
    private Instant updated_at;

    public Comment (User user, Song song, String text, Instant created_at, Instant updated_at) {
        this.user = user;
        this.song = song;
        this.text = text;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Comment () {
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

    public Song getSong () {
        return song;
    }

    public void setSong (Song song) {
        this.song = song;
    }

    public String getText () {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }

    public Instant getCreated_at () {
        return created_at;
    }

    public void setCreated_at (Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at () {
        return updated_at;
    }

    public void setUpdated_at (Instant updated_at) {
        this.updated_at = updated_at;
    }
}
