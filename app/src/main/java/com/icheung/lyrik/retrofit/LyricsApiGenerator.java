package com.icheung.lyrik.retrofit;

import com.icheung.lyrik.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class LyricsApiGenerator {
    private static volatile LyricsApi sApi;

    public static LyricsApi generate() {
        if(sApi == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }

            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LyricsApi.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(client)
                    .build();

            sApi = retrofit.create(LyricsApi.class);
        }

        return sApi;
    }
}