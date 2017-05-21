package com.icheung.lyrik.retrofit;

import com.icheung.lyrik.retrofit.model.Lyric;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LyricsApi {
    String BASE_URL = "http://lyrics.wikia.com/";

    @GET("api.php?func=getSong&fmt=xml")
    Call<Lyric> getLyrics(
            @Query("artist") String artist,
            @Query("song") String song
    );
}