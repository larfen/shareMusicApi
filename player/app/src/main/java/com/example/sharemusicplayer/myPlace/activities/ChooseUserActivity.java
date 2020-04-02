package com.example.sharemusicplayer.myPlace.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.myPlace.view.UserListAdapter;
import com.google.android.material.snackbar.Snackbar;

public class ChooseUserActivity extends AppCompatActivity {

    private RecyclerView userListView;
    private UserListAdapter userListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        // 设置导航栏
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);    // 不显示标题

        userListView = findViewById(R.id.user_list);
        userListView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        userListView.setLayoutManager(layoutManager);
        // 当点击时 切换播放列表
        userListAdapter = new UserListAdapter();

        userListView.setAdapter(userListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.save_menu, menu);

        MenuItem saveItem = menu.findItem(R.id.action_save);
        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Snackbar.make(myToolbar, "初始化成功!", Snackbar.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(ChooseUserActivity.this, MyPlaceActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return true;
    }
}
