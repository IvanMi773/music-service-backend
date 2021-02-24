package com.network.social_network.dto;

public class GenreDto {

    private String name;

    public GenreDto (String name) {
        this.name = name;
    }

    public GenreDto () {
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
}
