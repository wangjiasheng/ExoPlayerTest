package com.example.exoplayetest;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

public class mHlsMediaSource {
    public static HlsMediaSource getMediaSource(Context context){
        Uri contentUri = Uri.parse(Constant.ccty1);
        File cacheFile = new File(context.getExternalCacheDir().getAbsolutePath(), "video");
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getPackageName()));
        SimpleCache simpleCache = new SimpleCache(context.getCacheDir(),new NoOpCacheEvictor(),new ExoDatabaseProvider(context));
        CacheDataSourceFactory cacheDataSourceFactory=new CacheDataSourceFactory(simpleCache,dataSourceFactory);
        HlsMediaSource.Factory factory=new HlsMediaSource.Factory(cacheDataSourceFactory);
        return factory.createMediaSource(contentUri);
    }
    public static HlsMediaSource getMediaSource2(Context context,String url){
        Uri contentUri = Uri.parse(url);
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(context).build();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context,"useExoplayer"),bandwidthMeter);
        HlsMediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(contentUri);
        return videoSource;
    }
    public static HlsMediaSource getMediaSource3(Context context){
        Uri contentUri = Uri.parse(Constant.ccty1);
        File cacheFile = new File(context.getExternalCacheDir().getAbsolutePath(), "video");
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getPackageName()));
        SimpleCache simpleCache = new SimpleCache(cacheFile, new LeastRecentlyUsedCacheEvictor(512 * 1024 *     1024),new ExoDatabaseProvider(context)); // 本地最多保存512M, 按照LRU原则删除老数据
        CacheDataSourceFactory cachedDataSourceFactory = new CacheDataSourceFactory(simpleCache,    dataSourceFactory);
        HlsMediaSource.Factory factory=new HlsMediaSource.Factory(cachedDataSourceFactory);
        return factory.createMediaSource(contentUri);
    }
}
