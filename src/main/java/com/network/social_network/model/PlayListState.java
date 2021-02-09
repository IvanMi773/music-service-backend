package com.network.social_network.model;

public enum PlayListState {

    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private String state;

    PlayListState (String state) {
        this.state = state;
    }

    public String getState () {
        return state;
    }

    public void setState (String state) {
        this.state = state;
    }
}
