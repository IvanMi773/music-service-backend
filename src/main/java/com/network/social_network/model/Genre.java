package com.network.social_network.model;

import javax.persistence.*;

@Entity(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Genre (String name) {
        this.name = name;
    }

    public Genre () {
    }

    public Long getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
}
