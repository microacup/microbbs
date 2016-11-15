package me.micro.bbs.message;

import me.micro.bbs.enums.MessageType;

import java.util.Date;

/**
 * 消息
 *
 * Created by microacup on 2016/11/15.
 */
public class Message {

    private Long id;

    // 消息内容
    private String content;

    // 消息类型
    private MessageType type;

    // 消息生产者
    private Long authorId;

    // 消息消费者
    private Long targetUserId;

    // 所在话题
    private Long postId;

    // 所在回复
    private Long replyId;

    // 已读
    private Boolean hasRead = false;

    // 创建时间
    private Date createdTime;

}
