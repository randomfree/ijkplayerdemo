package com.ly.demo2.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.ly.demo2.R;
import com.ly.demo2.databinding.ActivityMainBinding;
import com.ly.demo2.live.RaceScheduleFragment;
import com.ly.demo2.main.recommend.RecommendFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private RecommendFragment recommendFragment;
    private RaceScheduleFragment raceScheduleFragment;
    private MineFragment mineFragment;

    private Fragment lastSelectFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment firstFragment = getFragmentByMenu(R.id.main_navigation_main);
        lastSelectFragment = firstFragment;

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            Fragment selectFragment = getFragmentByMenu(item.getItemId());
            if (selectFragment == null) {
                Log.e(TAG, "底部导航异常，点击的item没有匹配的fragment,请查看getFragmentByMenu方法");
                return true;
            }
            getSupportFragmentManager().beginTransaction().show(selectFragment).hide(lastSelectFragment).commit();
            lastSelectFragment = selectFragment;
            return true;
        });

    }

    private Fragment getFragmentByMenu(int menuId) {
        if (menuId == R.id.main_navigation_main) {
            if (recommendFragment == null) {
                recommendFragment = new RecommendFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container,recommendFragment).commit();
            }
            return recommendFragment;
        } else if (menuId == R.id.navigation_item_race) {
            if (raceScheduleFragment == null) {
                raceScheduleFragment = new RaceScheduleFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container,raceScheduleFragment).commit();
            }
            return raceScheduleFragment;
        } else if (menuId == R.id.navigation_item_mine) {
            if (mineFragment == null) {
                mineFragment = new MineFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container,mineFragment).commit();
            }
            return mineFragment;
        }
        return null;
    }


}