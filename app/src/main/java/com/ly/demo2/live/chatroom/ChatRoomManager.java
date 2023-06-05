package com.ly.demo2.live.chatroom;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ly.demo2.live.model.CommentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播聊天室管理类(测试)
 * <p>
 * 有一个情况需要考虑：
 * 客户端在发送评论/弹幕的时候有两种方案：
 * 1. 用户点击发送时，直接在评论区及弹幕区显示。
 * 如果是这种方案：
 * 1.用户自己发送的评论需要特殊处理，需要在接收消息时过滤掉用户发送的那条，以免重复；
 * 2.如果发送失败，只能不断重试，没有告知用户的机会。
 * 2.用户点击发送时，走聊天室进行正常发送，等待聊天室的回填，在onReceiveMessage中处理。
 * 这个方案的好处是：
 * 1. 不需要为了用户自己发评论的逻辑做特殊处理，所有弹幕和评论都走聊天室消息。
 * <p>
 * 不知道具体情况来想方案太空中楼阁了，感觉在自找烦恼···
 */
public class ChatRoomManager {


    public interface OnReceiveMessageListener {

        public void onReceiveMessage(CommentEntity entity);
    }


    private List<OnReceiveMessageListener> listeners = new ArrayList<>();

    private static ChatRoomManager instance = new ChatRoomManager();


    public static ChatRoomManager getInstance() {
        return instance;
    }


    public void addListener(LifecycleOwner lifecycle, OnReceiveMessageListener listener) {
        lifecycle.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                removeListener(listener);
            }
        });
        listeners.add(listener);
    }

    public void removeListener(OnReceiveMessageListener listener) {
        listeners.remove(listener);
    }


    /**
     * 接收聊天室消息
     *
     * @param entity
     */
    public synchronized void onReceiveMessage(CommentEntity entity) {
        for (OnReceiveMessageListener listener : listeners) {
            listener.onReceiveMessage(entity);
        }
    }

    /**
     * 发送聊天室消息
     *
     * @param entity
     */
    public void sendMessage(CommentEntity entity) {
        onReceiveMessage(entity);
    }

}
