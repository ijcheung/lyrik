package com.icheung.lyrik.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.icheung.lyrik.helper.SongAdapter;
import com.icheung.lyrik.retrofit.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongViewModel extends AndroidViewModel {
    private List<Song> songs;
    private SongAdapter adapter;

    public SongViewModel(Application application) {
        super(application);
    }

    public List<Song> getSongs() {
        if (songs == null) {
            songs = new ArrayList<Song>();
        }
        return songs;
    }

    public SongAdapter getAdapter() {
        if(adapter == null) {
            adapter = new SongAdapter(getSongs());
        }
        return adapter;
    }
}
