package com.network.social_network.mapper;

import com.network.social_network.dto.user.UserProfileDto;
import com.network.social_network.dto.user.UserRegistrationDto;
import com.network.social_network.model.PlayListState;
import com.network.social_network.model.Playlist;
import com.network.social_network.model.User;
import com.network.social_network.repository.PlaylistRepository;
import com.network.social_network.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    void userRegistrationDtoToUser () {
        var userRegistrationDto = new UserRegistrationDto(
                "asdf",
                "asdf",
                "asdf",
                "asdf",
                "asdf",
                1
        );

        var passwordEncoder = new BCryptPasswordEncoder(12);

        var user = UserMapper.instance.userRegistrationDtoToUser(userRegistrationDto, passwordEncoder);

        assertThat(user).isInstanceOf(User.class);
        assertThat(userRegistrationDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userRegistrationDto.getUsername()).isEqualTo(user.getEmail());
        assertThat(user.getPassword()).isNotNull();
        assertThat(userRegistrationDto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userRegistrationDto.getLastName()).isEqualTo(user.getLastName());
        assertThat("USER").isEqualTo(user.getRole());
    }

    @Test
    void userToUserProfileDto () {
        var user = new User(
                "asdf@asdf.com",
                "asdffadsadsf",
                "asdfadsffads",
                "asdfadsffdsa",
                "asdfdfsafsd",
                "asdfads.png",
                "USER",
                false
        );
        userRepository.save(user);

        var uploadsPlaylist = new Playlist(
                user,
                "Uploads",
                "default.png",
                PlayListState.TECHNICAL,
                false
        );
        ArgumentCaptor<Playlist> playlistArgumentCaptor = ArgumentCaptor.forClass(Playlist.class);
        playlistRepository.save(playlistArgumentCaptor.capture());

        var userProfileDto = UserMapper.instance.userToUserProfileDto(user);

        assertThat(userProfileDto).isInstanceOf(UserProfileDto.class);
        assertThat(userProfileDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userProfileDto.getUsername()).isEqualTo(user.getEmail());
        assertThat(userProfileDto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userProfileDto.getLastName()).isEqualTo(user.getLastName());
        assertThat(userProfileDto.getAvatar()).isEqualTo(user.getAvatar());
        assertThat(userProfileDto.getRole()).isEqualTo(user.getRole());

        userRepository.deleteById(user.getId());
        playlistRepository.deleteById(playlistArgumentCaptor.capture().getId());
    }
}