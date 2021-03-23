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
    private Integer duration;

    public SongFile () {
    }

    public SongFile (String fileName, Integer duration) {
        this.fileName = fileName;
        this.duration = duration;
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

    public Integer getDuration () {
        return duration;
    }

    public void setDuration (Integer duration) {
        this.duration = duration;
    }
}
