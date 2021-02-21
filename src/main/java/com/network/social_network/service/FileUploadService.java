package com.network.social_network.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    private final String storageDirectoryPath = "D:\\Projects\\music_service\\social_network\\src\\main\\resources\\storage";

    public void saveToStorage (MultipartFile files) {
        
    }
}
