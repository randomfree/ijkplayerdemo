package com.ly.demo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.os.Bundle;
import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.ly.demo2.databinding.ActivityMainBinding;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupPlayer();
        setupViewPager();
    }

    private void setupViewPager() {

        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment;
                switch (position){
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
        //
        ViewPager2Delegate.Companion.install(binding.viewPager,binding.tabLayout,false);
    }

    private void setupPlayer() {
        NiceVideoPlayer player = findViewById(R.id.textureView);
        player.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        player.setUp("rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp",null);

        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("美超：美国队长vs钢铁侠");
        controller.setImage(R.mipmap.poster);

        player.setController(controller);

        NiceVideoPlayerManager.instance().setCurrentNiceVideoPlayer(player);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}