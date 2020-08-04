package com.wjs.holder;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.exoplayetest.mHlsMediaSource;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import static com.google.android.exoplayer2.Player.STATE_BUFFERING;
import static com.google.android.exoplayer2.Player.STATE_ENDED;
import static com.google.android.exoplayer2.Player.STATE_IDLE;
import static com.google.android.exoplayer2.Player.STATE_READY;

public class ExoplayerImp implements PlayerBase{
    public SimpleExoPlayer exoPlayer;
    private PlayerProgressbarListener listener;
    @Override
    public void stop() {
        exoPlayer.setVideoSurface(null);
        exoPlayer.setPlayWhenReady(false);
        Log.i("wangjiasheng","ExoPlayer_stop");
    }

    @Override
    public void start() {
        exoPlayer.setPlayWhenReady(true);
        Log.i("wangjiasheng","ExoPlayer_start");
    }



    @Override
    public void pause() {
        exoPlayer.setVideoSurface(null);
        exoPlayer.setPlayWhenReady(false);
        Log.i("wangjiasheng","ExoPlayer_pause");
    }

    @Override
    public void playUrl(Context context,String url) {
        //exoPlayer.setVideoSurface(null);
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.prepare(mHlsMediaSource.getMediaSource2(context,url));
    }

    @Override
    public void prepare() {
        exoPlayer.setPlayWhenReady(true);
        Log.i("wangjiasheng","ExoPlayer_prepare");
    }

    @Override
    public void release() {
        Log.i("wangjiasheng","ExoPlayer_release");
        exoPlayer.release();
        exoPlayer=null;
        listener=null;
    }

    @Override
    public void setVideoSurface(SurfaceHolder surface) {
        exoPlayer.setVideoSurface(surface.getSurface());
        Log.i("wangjiasheng","ExoPlayer_setSurface");
    }

    @Override
    public void initPlayer(Context context,PlayerProgressbarListener listener) {
        Log.i("wangjiasheng","ExoPlayer_initPlayer");
        this.listener=listener;
        exoPlayer=new SimpleExoPlayer.Builder(context.getApplicationContext()).build();
        exoPlayer.addListener(new ExoPlayerrListener());
    }
    class ExoPlayerrListener implements Player.EventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState){
                case STATE_IDLE:
                    exoPlayer.setPlayWhenReady(true);
                    break;
                case STATE_BUFFERING://缓冲
                   if(listener!=null){
                       listener.showProgressBar();
                   }
                    break;
                case STATE_READY://就绪
                    if(listener!=null){
                        listener.hideProgressBar();
                    }
                    break;
                case STATE_ENDED://完成
                    break;
            }
        }

        @Override
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
            Log.i("wangjiasheng","onPlaybackSuppressionReasonChanged:"+playbackSuppressionReason);
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i("wangjiasheng","onPlayerError:"+error.getMessage());
            if(error.getMessage().indexOf("BehindLiveWindowException")>0){

            }
        }
    }
}
