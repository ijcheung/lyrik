package com.icheung.lyrik.helper;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.icheung.lyrik.R;
import com.icheung.lyrik.SongDetailActivity;
import com.icheung.lyrik.databinding.SongItemBinding;
import com.icheung.lyrik.retrofit.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private final List<Song> mSongs;

    public SongAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new ViewHolder((SongItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.song_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SongItemBinding binding = holder.getBinding();
        Song song = mSongs.get(position);
        binding.setSong(song);
        binding.executePendingBindings();
        if(TextUtils.isEmpty(song.getArtworkUrl())) {
            binding.thumb.setImageResource(0);
        } else {
            Glide.with(binding.thumb.getContext())
                    .load(song.getArtworkUrl())
                    .into(binding.thumb);
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public void loadSongs(List<Song> songs) {
        mSongs.clear();
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final SongItemBinding binding;

        ViewHolder(SongItemBinding itemBinding) {
            super(itemBinding.getRoot());

            binding = itemBinding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(binding.getRoot().getContext(), SongDetailActivity.class);

                    intent.putExtra(SongDetailActivity.EXTRA_SONG, mSongs.get(getAdapterPosition()));

                    binding.getRoot().getContext().startActivity(intent);
                }
            });
        }

        SongItemBinding getBinding() {
            return binding;
        }
    }
}
