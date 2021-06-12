package com.network.social_network.service;

import com.network.social_network.model.SongFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class FileUploadServiceTest {

    @Autowired
    private FileUploadService fileUploadService;

    @Test
    void savePlaylistPhoto () {
    }

    @Test
    void saveAvatars () {
    }

    @Test
    void saveSongCover () throws IOException {
        var imgFis = new FileInputStream("/home/ivan/Projects/music_service/pictures/asdf.jpg");
        var imgFile = new MockMultipartFile("test", "asdf.jpg", MediaType.IMAGE_JPEG_VALUE, imgFis.readAllBytes());

    }

    @Test
    void saveSong () throws IOException {
        var fis = new FileInputStream("/home/ivan/Projects/music_service/songs/3_11.mp3");
        var songFile = new MockMultipartFile(
                "test",
                "d.mp3",
                String.valueOf(MediaType.MULTIPART_MIXED),
                fis.readAllBytes()
        );
        assertThat(fileUploadService.saveSong(songFile)).isInstanceOf(SongFile.class);
    }
}