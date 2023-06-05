package com.ly.demo2.live.chatroom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.QuickViewHolder;
import com.ly.demo2.BaseFragment;
import com.ly.demo2.R;
import com.ly.demo2.databinding.FragmentChatRoomBinding;
import com.ly.demo2.databinding.LayoutChatRoomTextItemBinding;
import com.ly.demo2.live.DanmuControllInter;
import com.ly.demo2.live.OnActivityTouchInter;
import com.ly.demo2.live.model.CommentEntity;

public class ChatRoomFragment extends BaseFragment<FragmentChatRoomBinding> implements View.OnClickListener, OnActivityTouchInter {
    private static final String TAG = "ChatRoomFragment";


    private BaseQuickAdapter adapter = null;

    @Override
    protected FragmentChatRoomBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChatRoomBinding.inflate(inflater, container, false);
    }

    @Override
    protected void init() {
        initView();

        obseveDataChange();
    }

    private void obseveDataChange() {
        ChatRoomManager.getInstance().addListener(this, entity -> {
            adapter.add(entity);
            binding.recyclerView.scrollToPosition(adapter.getItems().size());
        });
    }


    private void initView() {

        initRecycleView();

        binding.newMessageTipView.setOnClickListener(this);
        binding.inputView.setOnEditorActionListener((v, actionId, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                //点击了发送

                getContentAndSend();
            }

            return true;
        });

        binding.sendCommentBtn.setOnClickListener(this);
        binding.hideGiftAnimaBtn.setOnClickListener(this);


    }

    private void getContentAndSend() {
        String text = binding.inputView.getText().toString().replace(" ", "").replace("\n", "");
        if (!TextUtils.isEmpty(text)) {
            ChatRoomManager.getInstance().sendMessage(new CommentEntity(text));
        }
    }

    private void initRecycleView() {
        adapter = new BaseQuickAdapter() {
            @NonNull
            @Override
            protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
                return new QuickViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_chat_room_text_item, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @Nullable Object o) {
                LayoutChatRoomTextItemBinding itemBinding = LayoutChatRoomTextItemBinding.bind(viewHolder.itemView);
                itemBinding.commentTv.setText(((CommentEntity) o).getContent());
            }
        };
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);


    }

    /**
     * 查看当前软键盘是否弹出
     *
     * @return
     */
    public boolean checkKeyboardStatus() {
        int[] location = new int[2];
        binding.inputView.getLocationOnScreen(location);

        /**
         * 软键盘开启
         */
        if (Math.abs(location[1] + binding.inputView.getHeight() - getScreenHeight()) > 200) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.newMessageTipView) {
            Toast.makeText(this.getContext(), "没问题", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.sendCommentBtn) {
            getContentAndSend();
        } else if (v.getId() == R.id.hideGiftAnimaBtn) {
            Activity activity = getActivity();
            if (activity instanceof DanmuControllInter) {
                boolean isShow = ((DanmuControllInter) activity).changeShowState();
                if (isShow) {
                    binding.hideGiftAnimaBtn.setImageResource(R.mipmap.gift_setting);
                } else {
                    binding.hideGiftAnimaBtn.setImageResource(R.mipmap.gift_setting_hide);
                }
            }
        }

    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Log.e(TAG, "width: " + width + ",height:" + height); //720,1560
        return height;
    }

    @Override
    public void onActivityTouch(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) return;
        if (checkKeyboardStatus()) {
            Rect inputBarRect = new Rect();
            //获取输入框这一个横栏在屏幕中的位置
            binding.bgView.getGlobalVisibleRect(inputBarRect);

            //如果点击的区域在输入框横栏的范围内，就不用隐藏，如果在其他区域，则隐藏软键盘
            if (!inputBarRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                hideSoftKeyboard(this.getActivity());
            }
        }
    }

}
