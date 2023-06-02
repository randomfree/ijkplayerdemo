package com.ly.demo2.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ly.demo2.BaseFragment;
import com.ly.demo2.databinding.FragmentMainRecommendBinding;

public class RecommendFragment extends BaseFragment<FragmentMainRecommendBinding> {

    @Override
    protected void initOnCreateView() {

    }

    @Override
    protected FragmentMainRecommendBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainRecommendBinding.inflate(inflater,container,false);
    }

}
