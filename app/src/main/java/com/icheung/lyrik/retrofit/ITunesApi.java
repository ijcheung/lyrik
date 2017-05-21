package com.icheung.lyrik.retrofit;

import com.icheung.lyrik.retrofit.model.Wrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITunesApi {
    String BASE_URL = "https://itunes.apple.com/";

    @GET("search")
    Call<Wrapper> getSongs(
            @Query("term") String term
    );
}