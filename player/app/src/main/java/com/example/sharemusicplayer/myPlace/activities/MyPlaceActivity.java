package com.example.sharemusicplayer.myPlace.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.musicPlayer.activities.PlayerActivity;
import com.example.sharemusicplayer.myPlace.view.MessageListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyPlaceActivity extends PlayerActivity {

    private RecyclerView messageListView;
    private MessageListAdapter messageListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar myToolbar;
    private FloatingActionButton newMessageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);

        // 设置导航栏
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示返回按钮

        messageListView = findViewById(R.id.message_list);
        messageListView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        messageListView.setLayoutManager(layoutManager);
        // 当点击时 切换播放列表
        messageListAdapter = new MessageListAdapter();

        messageListView.setAdapter(messageListAdapter);

        newMessageBtn = findViewById(R.id.new_message_btn);
        newMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlaceActivity.this, NewMessageActivity.class);
                startActivity(intent);
            }
        });
    }
}
