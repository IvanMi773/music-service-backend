package com.network.social_network.service;

import com.network.social_network.dto.song.SongRequestDto;
import com.network.social_network.dto.song.SongResponseDto;
import com.network.social_network.model.Genre;
import com.network.social_network.model.Song;
import com.network.social_network.repository.GenreRepository;
import com.network.social_network.repository.SongRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SongServiceTest {

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void getAll () {
        assertThat(songService.getAll()).isInstanceOf(ArrayList.class);
    }

    @Test
    void getSongsByUsername () {
        assertThat(songService.getSongsByUsername("admin")).isInstanceOf(ArrayList.class);
    }

    @Test
    void createSong () throws Exception {
        var fis = new FileInputStream("/home/ivan/Projects/music_service/songs/3_11.mp3");
        var imgFis = new FileInputStream("/home/ivan/Projects/music_service/pictures/asdf.jpg");
        var songFile = new MockMultipartFile("test", "d.mp3", String.valueOf(MediaType.MULTIPART_MIXED), fis.readAllBytes());
        var imgFile = new MockMultipartFile("test", "asdf.jpg", MediaType.IMAGE_JPEG_VALUE, imgFis.readAllBytes());
        var genre = new Genre("asdf");
        genreRepository.save(genre);
        var songRequestDto = new SongRequestDto("admin", "asdf", songFile, imgFile, genre.getId());
//        songService.createSong(songRequestDto);
    }

    @Test
    void deleteSongById () {
    }

    @Test
    void updateLikesOfSong () {
    }

    @Test
    void getSubscriptionsSongs () {
    }

    @Test
    void saveSongToPlaylist () {
    }

    @Test
    void getSongsByGenreName () {
    }
}