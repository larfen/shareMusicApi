package com.example.sharemusicplayer.musicPlayer.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.Song;
import com.example.sharemusicplayer.musicPlayer.view.SongsAdapter;

public class SongsActivity extends PlayerActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        recyclerView = findViewById(R.id.songs_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SongsAdapter(new Song[0], null);
        recyclerView.setAdapter(mAdapter);

    }
}
