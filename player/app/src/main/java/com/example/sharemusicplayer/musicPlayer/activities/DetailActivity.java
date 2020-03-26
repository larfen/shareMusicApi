package com.example.sharemusicplayer.musicPlayer.activities;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;

import com.andremion.music.MusicCoverView;
import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.musicPlayer.view.TransitionAdapter;

public class DetailActivity extends PlayerActivity {

    private MusicCoverView mCoverView;

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

    public void onFabClick(View view) {
        if (play()) {
            mCoverView.start();
        } else {
            mCoverView.stop();
        }
    }
}
