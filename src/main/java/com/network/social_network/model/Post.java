package com.network.social_network.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotBlank(message = "Text of post is required")
    private String text;

    @NotBlank(message = "Image is required")
    private String image;

    private Instant created_at;
    private Instant updated_at;

    public Post (User user, String text, String image, Instant created_at, Instant updated_at) {
        this.user = user;
        this.text = text;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Post () {
    }

    public Long getPostId () {
        return id;
    }

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public String getText () {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
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
