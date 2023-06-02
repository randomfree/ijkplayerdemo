package com.ly.demo2.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ly.demo2.BaseFragment;
import com.ly.demo2.databinding.FragmentMainMineBinding;

public class MineFragment extends BaseFragment<FragmentMainMineBinding> {


    @Override
    protected void initOnCreateView() {

    }

    @Override
    protected FragmentMainMineBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainMineBinding.inflate(inflater,container,false);
    }
}
