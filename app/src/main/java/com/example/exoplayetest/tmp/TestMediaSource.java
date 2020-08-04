package com.example.exoplayetest.tmp;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class TestMediaSource {
    private static String[] sources = new String[]{
            "http://video.wenwen.sogou.com/e75aa445e10f4a51cf9b1df72b947e40.mp4.f20.mp4",
            "http://video.wenwen.sogou.com/8640cc39cd2abaa644dbc9f4eef4b49e.mp4.f20.mp4",
            "http://video.wenwen.sogou.com/31bc728f5bd22e0cf6aa4898f7f78f0d.mp4.f20.mp4",
            "http://video.wenwen.sogou.com/8cd0ffa1bea136eef0b42057f305b7b0.mp4.f20.mp4",
            "http://video.wenwen.sogou.com/4dfd051e27a32ae319588e7066937398.mp4.f20.mp4",
    };
    public static MediaSource getMedia(Context context){
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(context).build();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context,"useExoplayer"),bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(sources[0]));
        return videoSource;
    }
}
