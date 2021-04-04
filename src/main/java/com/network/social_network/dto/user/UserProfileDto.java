package com.network.social_network.dto.user;

import com.network.social_network.model.User;

import java.util.Set;

public class UserProfileDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Set<User> subscriptions;
    private Set<User> subscribers;
    private Integer tracks;
    private String email;
    private String avatar;

    public UserProfileDto(
            Long id,
            String username,
            String firstName,
            String lastName,
            Set<User> subscriptions,
            Set<User> subscribers,
            Integer tracks,
            String email,
            String avatar
    ) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subscriptions = subscriptions;
        this.subscribers = subscribers;
        this.tracks = tracks;
        this.email = email;
        this.avatar = avatar;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Integer getTracks () {
        return tracks;
    }

    public void setTracks (Integer tracks) {
        this.tracks = tracks;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public Set<User>  getSubscriptions () {
        return subscriptions;
    }

    public void setSubscriptions (Set<User>  subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<User>  getSubscribers () {
        return subscribers;
    }

    public void setSubscribers (Set<User>  subscribers) {
        this.subscribers = subscribers;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getAvatar () {
        return avatar;
    }

    public void setAvatar (String avatar) {
        this.avatar = avatar;
    }
}
