package com.icheung.lyrik.retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="LyricsResult", strict = false)
public class Lyric {
    @Element(name = "lyrics")
    private String lyrics;

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}