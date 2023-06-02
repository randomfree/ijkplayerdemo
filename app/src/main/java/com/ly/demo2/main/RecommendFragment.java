package com.ly.demo2.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ly.demo2.BaseFragment;
import com.ly.demo2.databinding.FragmentMainRecommendBinding;

public class RecommendFragment extends BaseFragment<FragmentMainRecommendBinding> {

    @Override
    protected FragmentMainRecommendBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainRecommendBinding.inflate(inflater,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
