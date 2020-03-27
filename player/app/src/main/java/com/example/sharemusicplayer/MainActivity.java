package com.example.sharemusicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sharemusicplayer.musicPlayer.activities.DetailActivity;
import com.example.sharemusicplayer.musicPlayer.activities.PlayerActivity;
import com.example.sharemusicplayer.musicPlayer.activities.SearchActivity;
import com.example.sharemusicplayer.musicPlayer.fragment.MainFragment;
import com.example.sharemusicplayer.myPlace.fragment.MyPlaceFragment;
import com.example.sharemusicplayer.recommend.fragment.RecommendFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends PlayerActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    Fragment[] tabFragment = {new MainFragment(), new RecommendFragment(), new MyPlaceFragment()};
    String[] tabTitle;


    private View mTitleView;
    private View mTimeView;
    private View mDurationView;
    private View mProgressView;
    private View mFabView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置顶部导航栏
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 设置侧边栏
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        String[] temp = {getResources().getString(R.string.music_title), getResources().getString(R.string.discover_title), getResources().getString(R.string.my_area_title)};
        this.tabTitle = temp;
        // 设置pageView
        viewPager = findViewById(R.id.page_view);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), 1));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // 获取播放器动画视图
        mTitleView = findViewById(R.id.player_title);
        mTimeView = findViewById(R.id.player_time);
        mDurationView = findViewById(R.id.player_duration);
        mProgressView = findViewById(R.id.player_progress);
        mFabView = findViewById(R.id.player_fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 当options menu点击时事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println(item);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    /**
     * 定义pageTab切换的fragment
     */
    public class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return MainActivity.this.tabFragment[position];
        }

        @Override
        public int getCount() {
            return MainActivity.this.tabFragment.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return MainActivity.this.tabTitle[position];
        }
    }

    /**
     * 点击播放按钮时 播放或暂停音乐
     *
     * @param view
     */
    public void onFabClick(View view) {
        play();
    }

    /**
     * 当点击下方播放条时 进入播放详情activity
     *
     * @param view
     */
    public void onFooterLick(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(mTitleView, ViewCompat.getTransitionName(mTitleView)),
                new Pair<>(mTimeView, ViewCompat.getTransitionName(mTimeView)),
                new Pair<>(mDurationView, ViewCompat.getTransitionName(mDurationView)),
                new Pair<>(mProgressView, ViewCompat.getTransitionName(mProgressView)),
                new Pair<>(mFabView, ViewCompat.getTransitionName(mFabView)));
        ActivityCompat.startActivity(this, new Intent(this, DetailActivity.class), options.toBundle());
    }
}
