package com.ly.demo2.live;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ly.demo2.BaseFragment;
import com.ly.demo2.databinding.FragmentChatRoomBinding;

public class ChatRoomFragment extends BaseFragment<FragmentChatRoomBinding> {
    private static final String TAG = "ChatRoomFragment";


    @Override
    protected FragmentChatRoomBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChatRoomBinding.inflate(inflater,container,false);
    }

    @Override
    protected void init() {

    }
}
