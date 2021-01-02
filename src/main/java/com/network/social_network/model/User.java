package com.network.social_network.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Post> posts;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private Instant created_at;
    private Instant enabled_at;

    private String role;

    public User (String email, String username, String password, String firstName, String lastName, Instant created_at, Instant enabled_at, String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created_at = created_at;
        this.enabled_at = enabled_at;
        this.role = role;
    }

    public User() {
    }

    public Long getUserId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getEnabled_at() {
        return enabled_at;
    }

    public void setEnabled_at(Instant enabled_at) {
        this.enabled_at = enabled_at;
    }

    public String getRole () {
        return role;
    }

    public void setRole (String role) {
        this.role = role;
    }

    public List<Post> getPosts () {
        return posts;
    }

    public void setPosts (List<Post> posts) {
        this.posts = posts;
    }
}
