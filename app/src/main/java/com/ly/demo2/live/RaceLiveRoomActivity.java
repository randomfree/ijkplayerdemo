package com.ly.demo2.live;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.ly.demo2.R;
import com.ly.demo2.databinding.ActivityRaceLiveRoomBinding;
import com.ly.demo2.live.chatroom.ChatRoomFragment;
import com.ly.demo2.live.chatroom.ChatRoomManager;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

/**
 * 赛事直播间
 */
public class RaceLiveRoomActivity extends AppCompatActivity implements DanmuControllInter {
    private static final String TAG = "RaceLiveRoomActivity";
    private ActivityRaceLiveRoomBinding binding;

    private DanmuUiLogic danmuUiLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaceLiveRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //初始化view
        danmuUiLogic = new DanmuUiLogic();
        danmuUiLogic.init(this, this);
        setupPlayer();
        setupViewPager();
        binding.playerView.setDanmakuView(danmuUiLogic.getDanmakuView());
        //监听数据变化
        observeDataChange();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //主要帮助聊天室隐藏软键盘
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof OnActivityTouchInter) {
                ((OnActivityTouchInter) fragment).onActivityTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean changeShowState() {
        if (isFinishing() || isDestroyed()) return false;
        return danmuUiLogic.changeShowState();
    }

    private void observeDataChange() {
        ChatRoomManager.getInstance().addListener(this, entity -> {
            Log.i(TAG, "接收的消息:" + entity);
            danmuUiLogic.addDanmaku(CommentFormatter.formatForDanmu(entity));
        });
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
        binding.playerView.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        binding.playerView.setUp("rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp", null);
        binding.playerView.start();
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("美超：美国队长vs钢铁侠");
        controller.setImage(R.mipmap.poster);

        binding.playerView.setController(controller);

        NiceVideoPlayerManager.instance().setCurrentNiceVideoPlayer(binding.playerView);

    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
        danmuUiLogic.onBackPress();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }


}
