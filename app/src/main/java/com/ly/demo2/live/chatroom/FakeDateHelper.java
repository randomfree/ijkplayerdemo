package com.ly.demo2.live.chatroom;

import com.ly.demo2.live.model.CommentEntity;

import java.util.Random;

public class FakeDateHelper {

    private static String[] words = new String[]{
            "太好看了！",
            "不咋地啊",
            "挺不错啊",
            "一言难尽啊",
            "太精彩了",
            "看不懂了",
    };

    private static String[] names = new String[]{
            "张三",
            "李四",
            "王武",
            "钱六",
            "吴七",
            "欧阳娜娜",
    };


    private static Random random = new Random();
    public static CommentEntity generateCommenEntity(){
        CommentEntity entity = new CommentEntity("这比赛真是"+words[random.nextInt(words.length)]);
        entity.setNikeName(names[random.nextInt(names.length)]);
        return entity;
    }
}
