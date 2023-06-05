package com.ly.demo2.live.model;

/**
 * 评论数据实体
 * 直播中评论就是弹幕，所以暂时用同一个
 */
public class CommentEntity {

    private CommentEntity() {
    }

    public CommentEntity(String content){


        this.origin = ORIGIN_LOCAL;
        this.sendTime = System.currentTimeMillis();
        this.content = content;
        this.nikeName = "我：";
        //测试乱写的
        this.uid = 1;
    }



    /**
     * 评论来源于远程，基本可以理解为其他人
     * 实际情况中，自己发送的评论可能会被聊天室回填，可以用于判断是否是重复弹幕
     */
    public static final int ORIGIN_REMOTE = 1;

    /**
     * 自己本地发送的评论消息
     */
    public static final int ORIGIN_LOCAL = 2;

    /**
     * 消息来源
     * 可用常量：
     * {@link CommentEntity#ORIGIN_REMOTE}
     * {@link CommentEntity#ORIGIN_LOCAL}
     */
    private int origin;

    /**
     * 发送时的本地时间
     */
    private long sendTime;


    /**
     * 发送者的uid
     */
    private long uid;

    /**
     * 发送的内容
     */
    private String content;

    /**
     * 发送者的昵称
     */
    private String nikeName;


    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }


    @Override
    public String toString() {
        return "CommentEntity{" +
                "origin=" + origin +
                ", sendTime=" + sendTime +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                ", nikeName='" + nikeName + '\'' +
                '}';
    }
}
