package com.example.exoplayetest;

import android.net.Uri;

import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;

public class mRtmpMediaSource {
    public static MediaSource getMediaSource(){
        RtmpDataSource.Factory dataSourceFactory = new RtmpDataSourceFactory();
        ProgressiveMediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Constant.RTMP));
        return videoSource;
    }
}
