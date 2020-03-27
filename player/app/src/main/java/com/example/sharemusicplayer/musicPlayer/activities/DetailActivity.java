package com.example.sharemusicplayer.musicPlayer.activities;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;

import com.andremion.music.MusicCoverView;
import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.Song;
import com.example.sharemusicplayer.httpService.DownloadImageTask;
import com.example.sharemusicplayer.musicPlayer.view.TransitionAdapter;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class DetailActivity extends PlayerActivity {

    private MusicCoverView mCoverView;
    DisposableObserver<Song> sub;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mCoverView = (MusicCoverView) findViewById(R.id.cover);

        getWindow().getSharedElementEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                mCoverView.start();
            }
        });
    }

    public void rewindClick(View view) {
        before();
    }
    public void forwardClick(View view) {
        next();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sub.dispose();
    }

    /**
     * 当服务绑定完成后 获取当前播放的音乐 更改图片
     */
    @Override
    public void onServiceFinish() {
        sub = getNowPlayingSong().subscribeWith(new DisposableObserver<Song>() {
            @Override
            public void onNext(@NonNull Song song) {
                new DownloadImageTask(DetailActivity.this.mCoverView)
                        .execute(song.getPic_url());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void onFabClick(View view) {
        if (play()) {
            mCoverView.start();
        } else {
            mCoverView.stop();
        }
    }
}
