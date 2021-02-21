package com.network.social_network.model;

import javax.persistence.*;

@Entity(name = "song_files")
public class SongFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @OneToOne(mappedBy = "file")
    private Song song;

    private String fileName;
    private String fileType;
    private Double duration;

    public SongFile () {
    }

    public SongFile (String fileName, String fileType) {
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

    public Double getDuration () {
        return duration;
    }

    public void setDuration (Double duration) {
        this.duration = duration;
    }
}
