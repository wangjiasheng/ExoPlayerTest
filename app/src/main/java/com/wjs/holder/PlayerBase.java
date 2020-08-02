package com.wjs.holder;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.exoplayetest.Constant;

public interface PlayerBase {
    public void stop();
    public void start();
    public void pause();
    public void playUrl(Context context,String url);
    public void prepare();
    public void release();
    public void setVideoSurface(SurfaceHolder surface);
    public void initPlayer(Context context,PlayerProgressbarListener listener);
}
