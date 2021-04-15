package com.network.social_network.service;

import com.network.social_network.exception.CustomException;
import com.network.social_network.model.SongFile;
import com.network.social_network.repository.FilesRepository;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    private final FilesRepository filesRepository;

    private final Path playlistPhotos = Paths.get("uploads/playlist_photos");
    private final Path userAvatars = Paths.get("uploads/avatars");
    private final Path songCover = Paths.get("uploads/covers");

    private final Path songs = Paths.get("uploads/songs");
    private final String fileExpansion = ".mp3";

    public FileUploadService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public String savePlaylistPhoto (MultipartFile file) {
        return savePhoto(file, playlistPhotos);
    }

    public String saveAvatars (MultipartFile file) {
        return savePhoto(file, userAvatars);
    }

    public String saveSongCover (MultipartFile file) {
        return savePhoto(file, songCover);
    }

    public String savePhoto (MultipartFile file, Path root) {
        createRootDirectory(root);

        try {
            String filename = UUID.randomUUID() + "." + file.getContentType().split("/")[1];
            String path = root + "/" + filename;
            Files.write(Paths.get(path), file.getBytes());

            File image = new File(path);
            BufferedImage bufferedImage = ImageIO.read(image);
            var croppedImage = cropImage(bufferedImage, 0, 0, 1000, 1000);
            File pathFile = new File(path);
            ImageIO.write(croppedImage,"jpg", pathFile);

            return filename;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("io err", HttpStatus.MULTI_STATUS);
        }
    }

    private static BufferedImage cropImage (BufferedImage bufferedImage, int x, int y, int width, int height) {
        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        return croppedImage;
    }

    public SongFile saveSong (MultipartFile file) {
        createRootDirectory(songs);

        try {
            String filename = UUID.randomUUID() + fileExpansion;
            String path = songs + "/" + filename;
            Files.write(Paths.get(path), file.getBytes());

            File target = new File("/home/ivan/projects/music_service/music-service-backend/" + path);
            int duration;

            AudioFile audioFile = AudioFileIO.read(target);
            duration = audioFile.getAudioHeader().getTrackLength();

            SongFile model = new SongFile(filename, duration);
            filesRepository.save(model);

            return model;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("Saving song error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void createRootDirectory (Path root) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
