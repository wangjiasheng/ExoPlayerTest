package com.example.exoplayetest;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import java.util.UUID;

public class mDrmMediaSource {
    public DrmSessionManager<ExoMediaCrypto> getDrmType1(){
        DrmSessionManager<ExoMediaCrypto> drmSessionManager = DrmSessionManager.getDummyDrmSessionManager();
        return drmSessionManager;
    }
    public DrmSessionManager<ExoMediaCrypto> getDrmType2(){
        HttpDataSource.Factory licenseDataSourceFactory = new DefaultHttpDataSourceFactory("userAgent");
        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback("drmLicenseUrl", licenseDataSourceFactory);
        DrmSessionManager<ExoMediaCrypto> drmSessionManager = new DefaultDrmSessionManager.Builder()
                .setUuidAndExoMediaDrmProvider(UUID.fromString("uuid"), FrameworkMediaDrm.DEFAULT_PROVIDER)
                .build(drmCallback);
        return drmSessionManager;
    }
    public DrmSessionManager<ExoMediaCrypto> getType(){
        if(true){
            return getDrmType1();
        }else {
            return getDrmType2();
        }
    }
    public MediaSource ceateMediaSource(Context context){
        Uri contentUri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv");
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getPackageName()));
        @C.ContentType int type = Util.inferContentType(contentUri,null);
        if(type == C.TYPE_DASH){
            return new DashMediaSource.Factory(dataSourceFactory).setDrmSessionManager(getType()).createMediaSource(contentUri);
        }else if(type == C.TYPE_OTHER){
            return new ProgressiveMediaSource.Factory(dataSourceFactory).setDrmSessionManager(getType()).createMediaSource(contentUri);
        }
        return null;
    }
}
