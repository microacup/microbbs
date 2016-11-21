package me.micro.bbs.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.MessageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 消息
 *
 * Created by microacup on 2016/11/15.
 */
@Entity
@Table(name = "M_MESSAGE", indexes = {@Index(name = "IDX_TARGET", columnList = "m_targetUserId"),
        @Index(name = "IDX_HASREAD", columnList = "m_hasRead")})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 消息内容
    @Column(name = "m_content", nullable = false, length = 255)
    private String content;

    // 消息类型
    @Column(name = "p_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    // 消息生产者
    @Column(name = "m_authorId", nullable = false)
    private Long authorId;

    // 消息消费者
    @Column(name = "m_targetUserId", nullable = false)
    private Long targetUserId;

    // 所在话题
    @Column(name = "m_postId")
    private Long postId;

    // 所在回复
    @Column(name = "m_replyId")
    private Long replyId;

    // 所在回复
    @Column(name = "m_floor")
    private Long floor;

    // 已读
    @Column(name = "m_hasRead", nullable = false)
    private Boolean hasRead = false;

    // 创建时间
    @Column(name = "m_createdTime", nullable = false)
    private Date createdTime;

}
