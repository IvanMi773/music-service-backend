package com.network.social_network.dto.user;

import org.springframework.web.multipart.MultipartFile;

public class UserUpdateDto {

    private String email;
    private String firstName;
    private String lastName;
    private MultipartFile avatar;

    public UserUpdateDto (String email, String firstName, String lastName, MultipartFile avatar) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public MultipartFile getAvatar () {
        return avatar;
    }

    public void setAvatar (MultipartFile avatar) {
        this.avatar = avatar;
    }
}
