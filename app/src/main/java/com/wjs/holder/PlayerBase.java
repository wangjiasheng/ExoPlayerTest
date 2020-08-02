package com.wjs.holder;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public interface PlayerBase {
    public void stop();
    public void start();
    public void release();
    public void setVideoSurface(SurfaceHolder surface);
    public void initPlayer(Context context,String url,PlayerProgressbarListener listener);
}
