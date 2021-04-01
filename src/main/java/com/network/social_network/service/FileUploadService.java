package com.network.social_network.service;

import com.network.social_network.exception.CustomException;
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

    private final Path playlistPhotos = Paths.get("uploads/playlist_photos");
    private final Path userAvatars = Paths.get("uploads/avatars");

    public String savePlaylistPhoto (MultipartFile file) {
        return savePhoto(file, playlistPhotos);
    }

    public String saveAvatars (MultipartFile file) {
        return savePhoto(file, userAvatars);
    }

    public String savePhoto (MultipartFile file, Path root) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        try {
            String filename = String.valueOf(UUID.randomUUID());
            filename += "." + file.getContentType().split("/")[1];
            String path = root + "/" + filename;
            Files.write(Paths.get(path), file.getBytes());

            File image = new File(path);
            BufferedImage bufferedImage = ImageIO.read(image);
            var croppedImage = cropImage(bufferedImage, 0, 0, 600, 600);
            File pathFile = new File(path);
            ImageIO.write(croppedImage,"jpg", pathFile);

            return filename;
        } catch (Exception e) {
            //Todo: change exception
            throw new CustomException("io err", HttpStatus.MULTI_STATUS);
        }
    }

    public static BufferedImage cropImage (BufferedImage bufferedImage, int x, int y, int width, int height) {
        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        return croppedImage;
    }
}
