package com.network.social_network.mapping;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "songs", type = "song")
public class Song {

    @Id
    private Long id;

    private Long name;

    private String genre;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Long getName () {
        return name;
    }

    public void setName (Long name) {
        this.name = name;
    }

    public String getGenre () {
        return genre;
    }

    public void setGenre (String genre) {
        this.genre = genre;
    }
}
