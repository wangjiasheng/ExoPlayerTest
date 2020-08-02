package com.wjs.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class SurfaceViewHolder implements SurfaceHolder.Callback {
    private Context context;
    private SurfaceView surfaceView;
    private PlayerBase base;
    private PlayerProgressbarListener listener;
    public SurfaceViewHolder(Context context,SurfaceView surfaceView, PlayerProgressbarListener listener){
        this.surfaceView=surfaceView;
        this.context=context;
        this.listener=listener;
        surfaceView.getHolder().addCallback(this);
    }
    public void initPlayer(PlayerBase playerBase){
        if(playerBase==null){
            return;
        } else if(base!=null){
            base.stop();
            base.release();
        }
        this.base=playerBase;
        base.initPlayer(context,listener);

    }
    public void playerUrl(String url){
        base.playUrl(context,url);
        base.prepare();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("wangjiasheng","surfaceCreated"+Thread.currentThread());
        base.setVideoSurface(holder);
        base.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("wangjiasheng","surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("wangjiasheng","surfaceDestroyed");
        base.pause();
    }
    public void release(){
        base.release();
    }
}
