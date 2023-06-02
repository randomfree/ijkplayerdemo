package com.ly.demo2.main.recommend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ly.demo2.BaseFragment;
import com.ly.demo2.databinding.FragmentMainRecommendBinding;

public class RecommendFragment extends BaseFragment<FragmentMainRecommendBinding> {




    @Override
    protected void initOnCreateView() {

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
    }

    @Override
    protected FragmentMainRecommendBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainRecommendBinding.inflate(inflater,container,false);
    }

}
