package com.example.sharemusicplayer.recommend.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.Song;
import com.example.sharemusicplayer.httpService.BaseHttpService;
import com.example.sharemusicplayer.httpService.SongService;
import com.example.sharemusicplayer.musicPlayer.activities.PlayerActivity;
import com.example.sharemusicplayer.musicPlayer.view.SongsAdapter;
import com.example.sharemusicplayer.recommend.fragment.RecommendFragment;

import java.util.Arrays;

public class TopListActivity extends PlayerActivity {

    SongService songService = SongService.getInstance();
    private Song[] allSongs = {};
    private RecyclerView recyclerView;
    private SongsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_list_view);

        // 根据获取到的排行榜id获取歌曲信息
        Intent intent = getIntent();
        Long id = intent.getLongExtra(RecommendFragment.EXTRA_TOP_LIST_ID, 0L);
        songService.topList(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                // 列表数目10条10条显示
                allSongs = (Song[]) result.getData();
                mAdapter.setSongs(allSongs);
            }
        }, id);

        recyclerView = findViewById(R.id.songs_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 当点击时 切换播放列表
        mAdapter = new SongsAdapter(allSongs, new SongsAdapter.SongClickListener() {
            @Override
            public void onClick(Song song, int position) {
                setPlayList(allSongs, position);
                replay();
            }
        }, false);

        recyclerView.setAdapter(mAdapter);
    }
}
