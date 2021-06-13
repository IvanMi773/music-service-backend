package com.network.social_network.mapper;

import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegistrationDto.getPassword()))")
    @Mapping(target = "avatar", expression = "java(\"default_user.png\")")
    @Mapping(target = "role", expression = "java(userRegistrationDto.getRole() == 0 ? com.network.social_network.model.UserRole.ADMIN.getRole() : com.network.social_network.model.UserRole.USER.getRole())")
    @Mapping(target = "isDeleted", expression = "java(false)")
    User userRegistrationDtoToUser (
            UserRegistrationDto userRegistrationDto,
            PasswordEncoder passwordEncoder
    );

    @Mapping(target = "tracks", expression = "java(user.getPlaylists().size() >= 1 ? user.getPlaylists().get(0).getSongs().size() : 0)")
    UserProfileDto userToUserProfileDto (User user);
}
