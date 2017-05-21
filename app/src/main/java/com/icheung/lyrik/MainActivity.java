package com.icheung.lyrik;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.icheung.lyrik.helper.SongAdapter;
import com.icheung.lyrik.retrofit.ITunesApi;
import com.icheung.lyrik.retrofit.ITunesApiGenerator;
import com.icheung.lyrik.retrofit.model.Wrapper;
import com.icheung.lyrik.util.Utility;
import com.icheung.lyrik.viewmodel.SongViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private EditText mSearchTerm;
    private View mSearchButton;
    private View mLoading;

    private SongAdapter mAdapter;

    private ITunesApi mITunesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSearchTerm = (EditText) findViewById(R.id.search_term);
        mSearchButton = findViewById(R.id.search_button);
        mLoading = findViewById(R.id.loading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mITunesApi = ITunesApiGenerator.generate();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        SongViewModel songViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mAdapter = songViewModel.getAdapter();

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mSearchButton.setOnClickListener(this);
        // http://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext
        mSearchTerm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.search_button:
                doSearch();
                break;
        }
    }

    private void doSearch() {
        Call<Wrapper> call = mITunesApi.getSongs(mSearchTerm.getText().toString());

        mLoading.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Wrapper>() {
            @Override
            public void onResponse(Call<Wrapper> call, Response<Wrapper> response) {
                switch(response.code()) {
                    case 200:
                        Wrapper wrapper = response.body();

                        if(wrapper == null
                                || wrapper.getSongs() == null
                                || wrapper.getSongs().size() == 0) {
                            Utility.showError(MainActivity.this, R.string.error_no_songs);
                        } else {
                            mAdapter.loadSongs(wrapper.getSongs());
                        }
                        break;
                    default:
                        Utility.showError(MainActivity.this, R.string.error_connection);

                }
                mLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Wrapper> call, Throwable t) {
                Log.e(TAG, "onFailure", t);
                mLoading.setVisibility(View.INVISIBLE);
                Utility.showError(MainActivity.this, R.string.error_connection);
            }
        });
    }
}
