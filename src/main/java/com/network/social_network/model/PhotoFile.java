package com.network.social_network.model;

import javax.persistence.*;

@Entity(name = "photo_files")
public class PhotoFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @OneToOne(mappedBy = "photoFile")
    private Playlist playlist;

    private String fileName;
    private String fileType;

    public PhotoFile () {
    }

    public PhotoFile (String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Long getFileId () {
        return fileId;
    }

    public String getFileName () {
        return fileName;
    }

    public void setFileName (String fileName) {
        this.fileName = fileName;
    }

    public String getFileType () {
        return fileType;
    }

    public void setFileType (String fileType) {
        this.fileType = fileType;
    }

}
