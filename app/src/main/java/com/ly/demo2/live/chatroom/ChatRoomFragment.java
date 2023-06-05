package com.ly.demo2.live.chatroom;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ly.demo2.BaseFragment;
import com.ly.demo2.R;
import com.ly.demo2.databinding.FragmentChatRoomBinding;
import com.ly.demo2.live.OnActivityTouchInter;

public class ChatRoomFragment extends BaseFragment<FragmentChatRoomBinding> implements View.OnClickListener, OnActivityTouchInter {
    private static final String TAG = "ChatRoomFragment";


    @Override
    protected FragmentChatRoomBinding provideViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChatRoomBinding.inflate(inflater, container, false);
    }

    @Override
    protected void init() {

        initView();
    }


    private void initView() {
        binding.editMaskView.setOnClickListener(this);
        binding.newMessageTipView.setOnClickListener(this);

//        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (checkKeyboardStatus()) {
//                    binding.editMaskView.setClickable(true);
//                }else{
//                    binding.editMaskView.setClickable(false);
//                }
//            }
//        });
    }

    /**
     * 查看当前软键盘是否弹出
     * @return
     */
    public boolean checkKeyboardStatus(){
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
        if (v.getId() == R.id.editMaskView){
            hideSoftKeyboard(this.getActivity());
        } else if (v.getId() == R.id.newMessageTipView) {
            Toast.makeText(this.getContext(), "没问题", Toast.LENGTH_SHORT).show();
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
        if (event.getAction()!=MotionEvent.ACTION_DOWN)return;
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
