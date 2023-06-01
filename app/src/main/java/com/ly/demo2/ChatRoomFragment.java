package com.ly.demo2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ly.demo2.databinding.FragmentChatRoomBinding;

public class ChatRoomFragment extends Fragment {
    private static final String TAG = "ChatRoomFragment";

    /**
     * ！！！！任何public方法中使用binding需要判空以及生命周期
     */
    private FragmentChatRoomBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatRoomBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG,"onViewCreated------"+this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume------"+this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause------"+this);
    }
}
