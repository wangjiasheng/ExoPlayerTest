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
    private SurfaceHolder surfaceHolder;
    public SurfaceViewHolder(Context context,SurfaceView surfaceView, PlayerProgressbarListener listener){
        this.surfaceView=surfaceView;
        this.context=context;
        this.listener=listener;
        surfaceView.getHolder().addCallback(this);
    }
    public void initPlayer(PlayerBase playerBase,String url){
        if(playerBase==null){
            return;
        } else if(base!=null){
            base.stop();
            base.setVideoSurface(null);
            base.release();
        }
        this.base=playerBase;
        base.initPlayer(context,url,listener);
        if(surfaceHolder!=null){
            base.setVideoSurface(surfaceHolder);
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("wangjiasheng","surfaceCreated"+Thread.currentThread());
        surfaceHolder=holder;
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("wangjiasheng","surfaceChanged");
        surfaceHolder=holder;

       /* new Thread(){
            @Override
            public void run() {
                Rect rect=new Rect(0,0,width,height);
               Canvas canvas= holder.lockCanvas(rect);
               canvas.drawColor(Color.RED);
               holder.lockCanvas(rect);
            }
        }.start();*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("wangjiasheng","surfaceDestroyed");
        surfaceHolder=null;
        base.stop();
        base.setVideoSurface(null);
    }
    public void start(){
        base.setVideoSurface(surfaceHolder);
        base.start();
    }
}
