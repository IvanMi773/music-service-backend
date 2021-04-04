package com.network.social_network.mapping;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "users")
public class User {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "username")
    private String username;

    @Field(type = FieldType.Text, name = "firstName")
    private String firstName;

    @Field(type = FieldType.Text, name = "lastName")
    private String lastName;

    @Field(type = FieldType.Integer, name = "subscriptions")
    private Integer subscriptions;

    @Field(type = FieldType.Integer, name = "subscribers")
    private Integer subscribers;

    @Field(type = FieldType.Integer, name = "tracks")
    private Integer tracks;

    @Field(type = FieldType.Text, name = "avatar")
    private String avatar;

    public User(
            String username,
            String firstName,
            String lastName,
            Integer subscriptions,
            Integer subscribers,
            Integer tracks,
            String avatar
    ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subscriptions = subscriptions;
        this.subscribers = subscribers;
        this.tracks = tracks;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Integer subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public Integer getTracks() {
        return tracks;
    }

    public void setTracks(Integer tracks) {
        this.tracks = tracks;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
