package com.network.social_network.elasticsearch_models;

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

    public Song (Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Long getId() {
        return id;
    }

    public void setId(Long songId) {
        this.id = songId;
    }
}
