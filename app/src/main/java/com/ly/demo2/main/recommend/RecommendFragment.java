package com.ly.demo2.main.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.ly.demo2.BaseFragment;
import com.ly.demo2.databinding.FragmentMainRecommendBinding;

/**
 * 首页推荐fragment,tab和viewpager的容器
 */
public class RecommendFragment extends BaseFragment<FragmentMainRecommendBinding> {


    @Override
    protected void init() {
        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new RecommendListFragment();
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });
        ViewPager2Delegate.Companion.install(binding.viewPager,binding.tabLayout,false);
    }

    @Override
    protected FragmentMainRecommendBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainRecommendBinding.inflate(inflater,container,false);
    }

}
