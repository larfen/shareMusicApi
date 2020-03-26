package com.example.sharemusicplayer.musicPlayer.music;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import java.io.IOException;

public class PlayerService extends Service {
    private static final String TAG = PlayerService.class.getSimpleName();
    private static final int DURATION = 335;
    private MediaPlayer mediaPlayer;
    String[] songsUrl = {"http://tyst.migu.cn/public%2Fproduct5th%2Fproduct34%2F2019%2F07%2F1822%2F2009%E5%B9%B406%E6%9C%8826%E6%97%A5%E5%8D%9A%E5%B0%94%E6%99%AE%E6%96%AF%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F60054701923.mp3",
            "http://tyst.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F1618%2F2009%E5%B9%B406%E6%9C%8826%E6%97%A5%E5%8D%9A%E5%B0%94%E6%99%AE%E6%96%AF%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F60054701934.mp3"};
    int position = 1;
    boolean isPause = false;
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();


    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 初始化播放器
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 设置播放错误监听
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mediaPlayer.reset();
                return false;
            }
        });
        // 设置加载完成时
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        // 播放下一首
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next();
            }
        });
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        return super.onUnbind(intent);
    }

    public void play() {
        if (isPause) {
            mediaPlayer.start();
        } else {
            // 从当前位置循环播放
            mediaPlayer.reset();
            Uri uri = Uri.parse(songsUrl[position]);
            try {
                mediaPlayer.setDataSource(this, uri);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        isPause = false;
    }

    public void next() {
        position = (position + 1) % songsUrl.length;
        play();
    }
    public void before() {
        if (position == 0) {
            position = songsUrl.length - 1;
        } else {
            --position;
        }
        play();
    }


    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void pause() {
        if (isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    public int getPosition() {
        if (mediaPlayer != null && (isPlaying() || isPause)) {
            return mediaPlayer.getCurrentPosition() / 1000;
        }
        return 0;
    }

    public int getDuration() {
        if (mediaPlayer != null && (isPlaying() || isPause)) {
            return mediaPlayer.getDuration() / 1000;
        }
        return 0;
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {

        public PlayerService getService() {
            // Return this instance of PlayerService so clients can call public methods
            return PlayerService.this;
        }
    }
}
