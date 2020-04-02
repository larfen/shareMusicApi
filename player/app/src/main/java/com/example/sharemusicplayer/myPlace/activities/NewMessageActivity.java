package com.example.sharemusicplayer.myPlace.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sharemusicplayer.MainActivity;
import com.example.sharemusicplayer.R;

public class NewMessageActivity extends AppCompatActivity {
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);    // 不显示标题
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.save_menu, menu);

        MenuItem saveItem = menu.findItem(R.id.action_save);
        saveItem.setTitle("发表");
        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(NewMessageActivity.this, MainActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return true;
    }
}
