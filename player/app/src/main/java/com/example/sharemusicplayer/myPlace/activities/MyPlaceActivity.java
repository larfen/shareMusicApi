package com.example.sharemusicplayer.myPlace.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.Place;
import com.example.sharemusicplayer.httpService.BaseHttpService;
import com.example.sharemusicplayer.httpService.DownloadImageTask;
import com.example.sharemusicplayer.httpService.PlaceService;
import com.example.sharemusicplayer.musicPlayer.activities.PlayerActivity;
import com.example.sharemusicplayer.myPlace.view.MessageListAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * 我的圈子  根据圈子id获取显示的圈子
 */
public class MyPlaceActivity extends PlayerActivity {

    public static String PLACE_ID = "place_id";

    /**
     * 消息列表
     */
    private RecyclerView messageListView;
    private MessageListAdapter messageListAdapter;
    private RecyclerView.LayoutManager layoutManager;


    /**
     * 圈子信息
     */
    private Toolbar myToolbar;
    private FloatingActionButton newMessageBtn;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;

    /**
     * 圈子数据
     */
    private PlaceService placeService = PlaceService.getInstance();
    private Place place;    // 当前显示的圈子

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);

        // 设置导航栏
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        collapsingToolbarLayout = findViewById(R.id.toolbar_container);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示返回按钮

        /**
         * 设置消息列表
         */
        messageListView = findViewById(R.id.message_list);
        messageListView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        messageListView.setLayoutManager(layoutManager);
        messageListAdapter = new MessageListAdapter();
        messageListView.setAdapter(messageListAdapter);
        newMessageBtn = findViewById(R.id.new_message_btn);
        // 添加新消息时 进入发表消息界面
        newMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlaceActivity.this, NewMessageActivity.class);
                intent.putExtra(NewMessageActivity.PLACE_ID, place.getId());
                startActivity(intent);
            }
        });


        // 获取当前圈子 设置圈子信息
        imageView = findViewById(R.id.place_image);
        Long id = getIntent().getLongExtra(PLACE_ID, 0L);
        placeService.getById(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                place = (Place) result.getData();
                updateMessage(place);
            }
        }, id);
    }

    /**
     * 更新圈子信息
     *
     * @param place
     */
    private void updateMessage(Place place) {
        collapsingToolbarLayout.setTitle(place.getName());
        if (place.getPicUrl() != null && !place.getPicUrl().equals("")) {
            new DownloadImageTask(imageView).execute(BaseHttpService.BASE_URL + place.getPicUrl());
        }
    }
}
