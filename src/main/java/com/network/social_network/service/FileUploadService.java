package com.network.social_network.service;

import com.network.social_network.exception.CustomException;
import com.network.social_network.model.SongFile;
import com.network.social_network.repository.FilesRepository;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    private final Environment environment;

    private final Path songs = Paths.get("uploads/songs");
    private final String fileExpansion = ".mp3";

    @Value("upload.path")
    private String pathToSave;

    public FileUploadService (
            FilesRepository filesRepository,
            Environment environment
    ) {
        this.filesRepository = filesRepository;
        this.environment = environment;
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

    private String savePhoto (MultipartFile file, Path root) {
        createRootDirectory(root);

        try {
            String filename = UUID.randomUUID() + "." + file.getContentType().split("/")[1];
            String path = root + "/" + filename;
            Files.write(Paths.get(path), file.getBytes());

            File image = new File(path);
            BufferedImage bufferedImage = ImageIO.read(image);
//            var croppedImage = cropImage(bufferedImage, 0, 0, 500, 500);
            File pathFile = new File(path);
            ImageIO.write(bufferedImage,"jpg", pathFile);

            return filename;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("io err", HttpStatus.MULTI_STATUS);
        }
    }

    public SongFile saveSong (MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("empty file");
//            throw new FileEmptyException();
        }

        String path = "/home/ivan/Projects/music_service_backend/uploads/";
        System.out.println(pathToSave);
        String filename = UUID.randomUUID() + file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            var fileToSave = new File(path, filename);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileToSave));
            stream.write(bytes);
            stream.close();

            AudioFile audioFile = AudioFileIO.read(fileToSave);
            int duration = audioFile.getAudioHeader().getTrackLength();

            SongFile model = new SongFile(filename, duration);
            filesRepository.save(model);

            return model;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
