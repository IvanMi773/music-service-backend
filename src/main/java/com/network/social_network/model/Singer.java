//package com.network.social_network.model;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import java.util.List;
//
//@Entity(name = "singers")
//public class Singer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "singer_id")
//    private final Long id;
//
////    @OneToOne(mappedBy = "singers")
////    private User user;
//
////    @OneToMany
////    private List<Playlist> playLists;
//
////    private List<Playlist> albums;
//
//    @NotBlank(message = "Avatar is required")
//    private String avatar;
//
//    @NotBlank(message = "Description is required")
//    private String description;
//
//    public Singer (
//            Long id,
//            String avatar,
//            String description
//    ) {
//        this.id = id;
//        this.avatar = avatar;
//        this.description = description;
//    }
//
//    public Long getId () {
//        return id;
//    }
//
//    public String getAvatar () {
//        return avatar;
//    }
//
//    public void setAvatar (String avatar) {
//        this.avatar = avatar;
//    }
//
//    public String getDescription () {
//        return description;
//    }
//
//    public void setDescription (String description) {
//        this.description = description;
//    }
//}
