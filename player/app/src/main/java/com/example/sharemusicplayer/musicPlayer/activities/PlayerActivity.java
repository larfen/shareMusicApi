package com.example.sharemusicplayer.musicPlayer.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.Song;
import com.example.sharemusicplayer.httpService.BaseHttpService;
import com.example.sharemusicplayer.httpService.SongService;
import com.example.sharemusicplayer.musicPlayer.music.PlayerService;
import com.example.sharemusicplayer.musicPlayer.view.ProgressView;

/**
 * 所有播放器的基类
 * 与playerService进行通信 控制播放
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class PlayerActivity extends AppCompatActivity {

    private SongService songService = SongService.getInstance();

    // 动画切换场景
    private View mTitleView;
    private View mTimeAnimation;
    private View mDurationAnimation;
    private View mProgressAnimation;
    private View mFabView;

    private PlayerService mService;
    private boolean mBound = false;

    // 播放器组件
    private TextView mTimeView;
    private TextView mDurationView;
    private ProgressView mProgressView;
    private final Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final int position = mService.getPosition();
            final int duration = mService.getDuration();
            onUpdateProgress(position, duration);
            sendEmptyMessageDelayed(0, DateUtils.SECOND_IN_MILLIS);
        }
    };
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            onBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName classname) {
            mBound = false;
            onUnbind();
        }
    };

    /**
     * 更新播放器进度条
     * @param position
     * @param duration
     */
    private void onUpdateProgress(int position, int duration) {
        if (mTimeView != null) {
            mTimeView.setText(DateUtils.formatElapsedTime(position));
        }
        if (mDurationView != null) {
            mDurationView.setText(DateUtils.formatElapsedTime(duration));
        }
        if (mProgressView != null) {
            int percentage = (int) (position / (duration * 1.0) * 100);
            mProgressView.setProgress(percentage);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 获取播放器组件
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mTimeView = findViewById(R.id.player_time);
        mDurationView = findViewById(R.id.player_duration);
        mProgressView = findViewById(R.id.player_progress);
        mTitleView = findViewById(R.id.player_title);
        mFabView = findViewById(R.id.player_fab);
        mTimeAnimation = findViewById(R.id.player_time);
        mDurationAnimation = findViewById(R.id.player_duration);
        mProgressAnimation = findViewById(R.id.player_progress);
    }

    @Override
    protected void onDestroy() {
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        super.onDestroy();
    }


    /**
     * 点击播放按钮时 播放或暂停音乐
     * @param view
     */
    public void onFabClick(View view) {
        play();
    }

    /**
     * 当点击下方播放条时 进入播放详情activity
     * @param view
     */
    public void onFooterLick(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(mTitleView, ViewCompat.getTransitionName(mTitleView)),
                new Pair<>(mTimeAnimation, ViewCompat.getTransitionName(mTimeView)),
                new Pair<>(mDurationAnimation, ViewCompat.getTransitionName(mDurationView)),
                new Pair<>(mProgressAnimation, ViewCompat.getTransitionName(mProgressView)),
                new Pair<>(mFabView, ViewCompat.getTransitionName(mFabView)));
        ActivityCompat.startActivity(this, new Intent(this, DetailActivity.class), options.toBundle());
    }

    /**
     * 搜索歌曲
     * @param keyword
     * @param callBack
     */
    public void search(String keyword, BaseHttpService.CallBack callBack) {
        songService.search(callBack, keyword);
    }

    /**
     * 设置播放列表 此时播放位置会从头开始
     * @param songs
     */
    public void setPlayList(Song[] songs) {
        mService.settingPlayList(songs);
    }

    /**
     * 设置播放列表
     * @param songs
     * @param position
     */
    public void setPlayList(Song[] songs, int position) {
        mService.settingPlayList(songs);
        mService.setPosition(position);
    }

    /**
     * 从当前位置重新播放
     */
    public void replay() {
        mService.play();
    }

    /**
     * 播放音乐或暂停音乐
     * @return  返回播放或暂停音乐
     */
    public boolean play() {
        if (mService.isPlaying()) {
            mService.pause();
            return false;
        } else {
            mService.play();
            return true;
        }
    }

    /**
     * 播放下一首
     */
    public void next() {
        mService.next();
    }

    /**
     * 播放上一首
     */
    public void before() {
        mService.before();
    }

    private void onBind() {
        mUpdateProgressHandler.sendEmptyMessage(0);
    }

    private void onUnbind() {
        mUpdateProgressHandler.removeMessages(0);
    }
}
