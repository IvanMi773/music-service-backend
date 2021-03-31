//package com.network.social_network.model;
//
//import javax.persistence.*;
//import java.time.Instant;
//
//@Entity(name = "tokens")
//public class VerificationToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String token;
//
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private Instant expirationAt;
//
//    public VerificationToken (String token, User user, Instant expirationAt) {
//        this.token = token;
//        this.user = user;
//        this.expirationAt = expirationAt;
//    }
//
//    public VerificationToken () {
//    }
//
//    public Long getId () {
//        return id;
//    }
//
//    public String getToken () {
//        return token;
//    }
//
//    public void setToken (String token) {
//        this.token = token;
//    }
//
//    public User getUser () {
//        return user;
//    }
//
//    public void setUser (User user) {
//        this.user = user;
//    }
//
//    public Instant getExpirationAt () {
//        return expirationAt;
//    }
//
//    public void setExpirationAt (Instant expirationAt) {
//        this.expirationAt = expirationAt;
//    }
//}
