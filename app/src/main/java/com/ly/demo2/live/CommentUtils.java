package com.ly.demo2.live;

import com.ly.demo2.live.model.CommentEntity;

public class CommentUtils {


    /**
     * 根据原始数据获取评论区的展示内容
     *
     * @param entity
     * @return
     */
    public static String formatCommentContentForComment(CommentEntity entity) {
        return entity.getContent();
    }


    /**
     * 根据原始数据获取弹幕区的展示内容
     *
     * @param entity
     * @return
     */
    public static String formatCommentContentForDanmu(CommentEntity entity) {
        return entity.getContent();
    }

}
