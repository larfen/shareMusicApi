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
        mCoverView.setCallbacks(new MusicCoverView.Callbacks() {
            @Override
            public void onMorphEnd(MusicCoverView coverView) {
                // Nothing to do
            }

            @Override
            public void onRotateEnd(MusicCoverView coverView) {
                supportFinishAfterTransition();
            }
        });

        getWindow().getSharedElementEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                DetailActivity.this.play();
                mCoverView.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        onFabClick(null);
    }

    public void onFabClick(View view) {
        pause();
        mCoverView.stop();
    }
}
