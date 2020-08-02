package com.wjs.holder;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MediaPlayerImpl implements PlayerBase {
    private MediaPlayer mediaPlayer;
    @Override
    public void stop() {
        mediaPlayer.stop();
        Log.i("wangjiasheng","MediaPlayer_stop");
    }

    @Override
    public void start() {
       mediaPlayer.start();
        Log.i("wangjiasheng","MediaPlayer_start");
    }

    @Override
    public void release() {
        mediaPlayer.release();
        mediaPlayer=null;
    }

    @Override
    public void setVideoSurface(SurfaceHolder surface) {
        Log.i("wangjiasheng","MediaPlayer_setVideoSurface");
        mediaPlayer.setDisplay(surface);
    }

    @Override
    public void initPlayer(Context context,String url, PlayerProgressbarListener listener) {
        try {
            Log.i("wangjiasheng","initMeidaPlayer");
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i("wangjiasheng","MeidaPlayer_onPrepared");
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i("wangjiasheng","MediaPlaeyr_onError"+what);
                    return false;
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what){
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            if(listener!=null){
                                listener.showProgressBar();
                            }
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            if(listener!=null){
                                listener.hideProgressBar();
                            }
                            break;
                    }
                    return false;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
