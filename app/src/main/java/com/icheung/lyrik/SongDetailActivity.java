package com.icheung.lyrik;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.icheung.lyrik.retrofit.LyricsApi;
import com.icheung.lyrik.retrofit.LyricsApiGenerator;
import com.icheung.lyrik.retrofit.model.Lyric;
import com.icheung.lyrik.retrofit.model.Song;
import com.icheung.lyrik.util.Utility;
import com.icheung.lyrik.viewmodel.LyricViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongDetailActivity extends AppCompatActivity {
    private static final String TAG = SongDetailActivity.class.getSimpleName();
    public static String EXTRA_SONG = "song";

    private TextView mTitle;
    private TextView mMeta;
    private TextView mLyrics;
    private View mLoading;

    private LyricsApi mApi;
    private Song mSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        mTitle = (TextView) findViewById(R.id.title);
        mMeta = (TextView) findViewById(R.id.meta);
        mLyrics = (TextView) findViewById(R.id.lyrics);
        mLoading = findViewById(R.id.loading);

        mApi = LyricsApiGenerator.generate();
        mSong = getIntent().getParcelableExtra(EXTRA_SONG);

        mTitle.setText(mSong.getTrackName());
        mMeta.setText(mSong.getArtistName() + " - " + mSong.getAlbumName());

        final LyricViewModel lyricViewModel = ViewModelProviders.of(this).get(LyricViewModel.class);
        String lyrics = lyricViewModel.getLyrics();

        if(TextUtils.isEmpty(lyrics)) {
            Call<Lyric> call = mApi.getLyrics(mSong.getArtistName(), mSong.getTrackName());
            call.enqueue(new Callback<Lyric>() {
                @Override
                public void onResponse(Call<Lyric> call, Response<Lyric> response) {
                    switch(response.code()) {
                        case 200:
                            if(response.body() == null || TextUtils.isEmpty(response.body().getLyrics())) {
                                Utility.showError(SongDetailActivity.this, R.string.error_no_lyrics);
                            } else {
                                mLyrics.setText(response.body().getLyrics());
                                mLyrics.setVisibility(View.VISIBLE);
                                lyricViewModel.setLyrics(response.body().getLyrics());
                            }
                            break;
                        default:
                            Utility.showError(SongDetailActivity.this, R.string.error_connection);
                    }
                    mLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Lyric> call, Throwable t) {
                    Log.e(TAG, "onFailure", t);
                    Utility.showError(SongDetailActivity.this, R.string.error_connection);
                    mLoading.setVisibility(View.GONE);
                }
            });
        } else {
            mLyrics.setText(lyrics);
            mLyrics.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.GONE);
        }
    }
}
