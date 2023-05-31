package com.ly.demo2;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MTTextureView extends FrameLayout implements TextureView.SurfaceTextureListener {

    public MTTextureView(@NonNull Context context) {
        super(context);
        init();
    }

    public MTTextureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MTTextureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    TextureView textureView;
    IjkMediaPlayer ijkPlayer;
    private void init(){
        textureView =new TextureView(getContext());
        textureView.setSurfaceTextureListener(this);
        this.addView(textureView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);


    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {

        try {

//            String url = "http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8";
            String url = " rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp";
            ijkPlayer = new IjkMediaPlayer();

            ijkPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(IMediaPlayer iMediaPlayer, int what, int i1) {
                    if (what == -10000) {
                        // 执行重试操作，例如重新设置播放地址并开始播放
//                        try {
//                            ijkPlayer.setDataSource(url);
//                        } catch (IOException e) {
//
//                        }
                        ijkPlayer.reset();
                        try {
                            ijkPlayer.setDataSource(url);
                        } catch (IOException e) {


                        }
                        ijkPlayer.setSurface(new Surface(surface));
                        ijkPlayer.prepareAsync();
                        ijkPlayer.start();

                    }
                    return false;
                }
            });

//            String url = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4";
            ijkPlayer.setDataSource(url);
            ijkPlayer.setSurface(new Surface(surface));
            ijkPlayer.prepareAsync();
            ijkPlayer.start();
        } catch (IOException e) {
            Log.e("tag","msg",e);
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }
}
