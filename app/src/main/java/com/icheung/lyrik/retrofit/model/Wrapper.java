package com.icheung.lyrik.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wrapper {
    @SerializedName("results")
    List<Song> songs;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
