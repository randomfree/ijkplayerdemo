package com.ly.demo2.live;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ly.demo2.util.CommonUtil;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class DanmuUiLogic {

    private static final String TAG = "DanmuUiLogic";

    /**
     * 弹幕view
     */
    private DanmakuView danmakuView;

    /**
     * 弹幕上下文
     */
    private DanmakuContext danmakuContext;

    private Context context;


    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };


    public void init(Context context, LifecycleOwner lifecycleOwner){
        this.context = context;
        lifecycleOwner.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_RESUME){
                    if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
                        danmakuView.resume();
                    }
                } else if (event == Lifecycle.Event.ON_PAUSE) {
                    if (danmakuView != null && danmakuView.isPrepared()) {
                        danmakuView.pause();
                    }
                } else if (event == Lifecycle.Event.ON_DESTROY) {
                    if (danmakuView != null) {
                        danmakuView.release();
                    }
                }
            }
        });


        danmakuView = new DanmakuView(context);
        setupDanmu();
    }

    private void setupDanmu() {
        if (context == null){
            Log.e(TAG,"context = null 没有初始化");
            return;
        }
        if (danmakuView == null){
            danmakuView = new DanmakuView(context);
        }

        danmakuView.enableDanmakuDrawingCache(true);

        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
//                showDanmaku = true;
                if (danmakuView != null) {
                    danmakuView.start();
                }
//                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {
//                    timer.add((long) (timer.lastInterval() * (2 - 1)));

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);

    }

    public boolean changeShowState(){
        if (danmakuView.getVisibility() == View.VISIBLE) {
            danmakuView.setVisibility(View.GONE);
            return false;
        } else {
            danmakuView.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public DanmakuView getDanmakuView(){
        return danmakuView;
    }

    public void addDanmaku(String content) {
        //todo:草率处理一下，正常应该给个buff
        if (danmakuView == null || !danmakuView.isPrepared()||context == null) return;
        if (TextUtils.isEmpty(content))return;
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = CommonUtil.sp2px(context,20);
        danmaku.textColor = Color.GREEN;
        danmaku.setTime(danmakuView.getCurrentTime());

        danmakuView.addDanmaku(danmaku);
    }

    public void onBackPress(){
        if (danmakuView != null) {
            danmakuView.release();
        }
    }
}
