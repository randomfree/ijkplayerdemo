package com.ly.demo2.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.ly.demo2.databinding.ActivityMainBinding;
import com.ly.demo2.live.ChatRoomFragment;
import com.ly.demo2.live.RaceScheduleFragment;
import com.ly.demo2.live.RankFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.viewPager.setCurrentItem(item.getOrder(),false);
                return true;
            }
        });

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

    }


}