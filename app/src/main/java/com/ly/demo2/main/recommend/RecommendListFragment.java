package com.ly.demo2.main.recommend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ly.demo2.BaseFragment;
import com.ly.demo2.databinding.FragmentRecommendListBinding;

public class RecommendListFragment extends BaseFragment<FragmentRecommendListBinding> {
    @Override
    protected void initOnCreateView() {

    }

    @Override
    protected FragmentRecommendListBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRecommendListBinding.inflate(inflater,container,false);
    }
}
