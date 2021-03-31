package com.network.social_network.dto.user;

import java.util.Set;

public class UserLibraryDto {

    private Set<UserProfileDto> subscribers;
    private Set<UserProfileDto> subscriptions;

    public UserLibraryDto (Set<UserProfileDto> subscribers, Set<UserProfileDto> subscriptions) {
        this.subscribers = subscribers;
        this.subscriptions = subscriptions;
    }

    public Set<UserProfileDto> getSubscribers () {
        return subscribers;
    }

    public void setSubscribers (Set<UserProfileDto> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<UserProfileDto> getSubscriptions () {
        return subscriptions;
    }

    public void setSubscriptions (Set<UserProfileDto> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
