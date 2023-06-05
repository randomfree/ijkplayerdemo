package com.ly.demo2.live;

import com.ly.demo2.live.model.CommentEntity;

public class CommentFormatter {


    /**
     * 根据原始数据获取评论区的展示内容
     *
     * @param entity
     * @return
     */
    public static String formatForComment(CommentEntity entity) {
        return entity.getNikeName()+":"+entity.getContent();
    }


    /**
     * 根据原始数据获取弹幕区的展示内容
     *
     * @param entity
     * @return
     */
    public static String formatForDanmu(CommentEntity entity) {
        return entity.getContent();
    }

}
