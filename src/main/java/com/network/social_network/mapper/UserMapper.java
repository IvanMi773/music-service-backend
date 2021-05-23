package com.network.social_network.mapper;

import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegistrationDto.getPassword()))")
    @Mapping(target = "avatar", expression = "java(\"default_user.png\")")
    @Mapping(target = "role", expression = "java(userRegistrationDto.getRole() == 0 ? com.network.social_network.model.UserRole.ADMIN.getRole() : com.network.social_network.model.UserRole.USER.getRole())")
    @Mapping(target = "isDeleted", expression = "java(false)")
    User userRegistrationDtoToUser (
            UserRegistrationDto userRegistrationDto,
            PasswordEncoder passwordEncoder
    );

    @Mapping(target = "tracks", expression = "java(user.getPlaylists().get(0).getSongs().size())")
    UserProfileDto userToUserProfileDto (User user);

    com.network.social_network.elasticsearch_models.User userToElasticSearchUser (User user);
}
