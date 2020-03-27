package com.example.sharemusicplayer.musicPlayer.activities;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.Song;
import com.example.sharemusicplayer.httpService.BaseHttpService;
import com.example.sharemusicplayer.musicPlayer.view.SongsAdapter;

public class SearchActivity extends PlayerActivity {

    private RecyclerView recyclerView;
    private SongsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Song[] searchSongs = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);    // 不显示标题

        recyclerView = findViewById(R.id.songs_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 当点击时 切换播放列表
        mAdapter = new SongsAdapter(searchSongs, new SongsAdapter.SongClickListener() {
            @Override
            public void onClick(Song song, int position) {
                setPlayList(searchSongs, position);
                replay();
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.serach_menu, menu);

        // 当搜索确认时 发送后台请求获取歌曲信息
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchActivity.this.search(query, new BaseHttpService.CallBack() {
                    @Override
                    public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                        searchSongs = (Song[]) result.getData();
                        mAdapter.setSongs(searchSongs);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
