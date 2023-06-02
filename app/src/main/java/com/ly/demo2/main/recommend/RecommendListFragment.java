package com.ly.demo2.main.recommend;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.DataBindingHolder;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.ly.demo2.BaseFragment;
import com.ly.demo2.R;
import com.ly.demo2.databinding.FragmentRecommendListBinding;
import com.ly.demo2.databinding.ItemRecommendListBinding;
import com.ly.demo2.live.RaceLiveRoomActivity;
import com.ly.demo2.main.recommend.model.RecommendItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛事列表
 */
public class RecommendListFragment extends BaseFragment<FragmentRecommendListBinding> {


    @Override
    protected void init() {

        this.binding.recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2, RecyclerView.VERTICAL,false));
        

        BaseQuickAdapter adapter  = new BaseQuickAdapter() {
            @NonNull
            @Override
            protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
                return new QuickViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recommend_list,viewGroup,false));
            }

            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @Nullable Object o) {

            }

            @Override
            protected int getItemCount(@NonNull List items) {
                return Integer.MAX_VALUE;
            }
        };

        adapter.setOnItemClickListener((baseQuickAdapter, view, i) -> startActivity(new Intent(view.getContext(), RaceLiveRoomActivity.class)));
        this.binding.recyclerView.setAdapter(adapter);

    }

    @Override
    protected FragmentRecommendListBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRecommendListBinding.inflate(inflater,container,false);
    }
}
