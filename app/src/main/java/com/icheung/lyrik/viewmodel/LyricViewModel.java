package com.icheung.lyrik.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

public class LyricViewModel extends AndroidViewModel {
    private String lyrics;

    public LyricViewModel(Application application) {
        super(application);
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
