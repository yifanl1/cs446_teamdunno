package com.example.cosine.myapplication;

public class Song {
    private final String filePath;
    private final String title;

    public Song(String filePath, String title) {
        this.filePath = filePath;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getFilePath() {
        return filePath;
    }
}
