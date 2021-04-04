package com.network.social_network.mapping;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "songs")
public class Song {

    @Id
    private String indexId;

    @Field(type = FieldType.Long, name = "songId")
    private Long id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Text, name = "genre")
    private String genre;

    @Field(type = FieldType.Text, name = "username")
    private String username;

    @Field(type = FieldType.Text, name = "file")
    private String file;

    @Field(type = FieldType.Integer, name = "duration")
    private Integer duration;

    public Song(Long id, String name, String genre, String username, String file, Integer duration) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.username = username;
        this.file = file;
        this.duration = duration;
    }

    public String getIndexId () {
        return indexId;
    }

    public void setIndexId (String id) {
        this.indexId = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getGenre () {
        return genre;
    }

    public void setGenre (String genre) {
        this.genre = genre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long songId) {
        this.id = songId;
    }
}
