package com.ly.demo2.live;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.ly.demo2.R;
import com.ly.demo2.databinding.ActivityRaceLiveRoomBinding;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.HashMap;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class RaceLiveRoomActivity extends AppCompatActivity {
    private static final String TAG = "RaceLiveRoomActivity";
    private ActivityRaceLiveRoomBinding binding;

    /**
     * 弹幕上下文
     */
    private DanmakuContext danmakuContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaceLiveRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupPlayer();
        setupViewPager();
        setupDanmu();



        start();
//        addDanmaku(true);

    }
    Runnable run =new Runnable() {
        @Override
        public void run() {
            start();
            if (!binding.danmuView.isPrepared())return;
            addDanmaku(false);

        }
    };
    private void start(){

        binding.danmuView.postDelayed(run,50);
    }

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    private void setupDanmu() {
        binding.danmuView.enableDanmakuDrawingCache(true);

        binding.danmuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
//                showDanmaku = true;
                binding.danmuView.start();
//                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        binding.danmuView.prepare(parser, danmakuContext);

    }

    private void addDanmaku(boolean islive) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = "content";
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.GREEN;
        danmaku.setTime(binding.danmuView.getCurrentTime());

        binding.danmuView.addDanmaku(danmaku);
    }
    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    private void setupViewPager() {

        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment;
                switch (position) {
                    case 0:
                        fragment = new ChatRoomFragment();
                        break;
                    case 1:
                        fragment = new RankFragment();
                        break;
                    case 2:
                    default:
                        fragment = new RaceScheduleFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });
        //不要修改forceSmoothScroll参数的值
        //因为一旦该为true，那么用户在点击第二个tab的时候会创建第三个fragment
        //如果是false就不会
        //就是这么神奇
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabLayout, false);
    }

    private void setupPlayer() {
        NiceVideoPlayer player = findViewById(R.id.textureView);
        player.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        player.setUp("rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp", null);

        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("美超：美国队长vs钢铁侠");
        controller.setImage(R.mipmap.poster);

        player.setController(controller);

        NiceVideoPlayerManager.instance().setCurrentNiceVideoPlayer(player);
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();

        binding.danmuView.release();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (binding.danmuView.isPrepared()&&binding.danmuView.isPaused()){
            binding.danmuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (binding.danmuView.isPrepared()) {
            binding.danmuView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.danmuView.release();
    }


}
