package com.network.social_network.dto.user;

public class UserProfileDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Integer subscriptions;
    private Integer subscribers;
    private Integer tracks;
    private String email;

    public UserProfileDto (
            Long id,
            String username,
            String firstName,
            String lastName,
            Integer subscriptions,
            Integer subscribers,
            Integer tracks,
            String email
    ) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subscriptions = subscriptions;
        this.subscribers = subscribers;
        this.tracks = tracks;
        this.email = email;
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

    public Integer getSubscriptions () {
        return subscriptions;
    }

    public void setSubscriptions (Integer subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Integer getSubscribers () {
        return subscribers;
    }

    public void setSubscribers (Integer subscribers) {
        this.subscribers = subscribers;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }
}
