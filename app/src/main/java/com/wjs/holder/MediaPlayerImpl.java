package com.wjs.holder;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.exoplayetest.Constant;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

                public class MediaPlayerImpl implements PlayerBase {
    public static final int IDEL=10001;
    public static final int Initialized=10002;
    public static final int Prepared=10003;
    public static final int Started=10004;
    public static final int PlaybackCompleted=10005;
    public static final int Stopped=10006;
    public static final int Paused=10007;
    public static final int Preparing=10008;
    public static final int End=10009;
    public static final int Error=10010;
    public int currentStatus=IDEL;
    private MediaPlayer mediaPlayer;
    @Override
    public void stop() {
        if(currentStatus==PlaybackCompleted||currentStatus==Paused||currentStatus==Prepared||currentStatus==Started){
            Log.i("wangjiasheng","MediaPlayer_stop");
            mediaPlayer.stop();
            mediaPlayer.setDisplay(null);
            currentStatus=Stopped;
        }else{
            Log.i("wangjiasheng","stop状态错误:"+currentStatus);
        }
    }

    @Override
    public void start() {
        if(currentStatus==Paused||currentStatus==PlaybackCompleted||currentStatus==Prepared) {
            mediaPlayer.start();
            currentStatus=Started;
        }else{
            Log.i("wangjiasheng","start状态错误:"+currentStatus);
        }
    }

    @Override
    public void prepare()  {
        try {
            if(currentStatus==Initialized || currentStatus==Stopped) {
                Log.i("wangjiasheng", "MediaPlayer_start"+currentStatus);
                mediaPlayer.prepareAsync();
                currentStatus = Preparing;
            }else{
                Log.i("wangjiasheng","prepare状态错误:"+currentStatus);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void pause() {
        if(currentStatus==Started) {
            mediaPlayer.pause();
            currentStatus=Paused;
        }else{
            Log.i("wangjiasheng","pause状态错误:"+currentStatus);
        }
    }

    @Override
    public void playUrl(Context context,String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            currentStatus=Initialized;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void release() {
        Log.i("wangjiasheng","MediaPlayer_release");
        mediaPlayer.release();
        currentStatus=End;
        mediaPlayer=null;
    }

    @Override
    public void setVideoSurface(SurfaceHolder surface) {
        Log.i("wangjiasheng","MediaPlayer_setVideoSurface");
        mediaPlayer.setDisplay(surface);
    }

    @Override
    public void initPlayer(Context context, PlayerProgressbarListener listener) {
        try{
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    currentStatus=Error;
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
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i("wangjiasheng","MeidaPlayer_onPrepared");
                    currentStatus=Prepared;
                    mediaPlayer.start();
                    currentStatus=Started;

                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
