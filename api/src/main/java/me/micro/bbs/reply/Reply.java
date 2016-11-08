package me.micro.bbs.reply;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.post.Post;
import me.micro.bbs.user.User;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * 回复
 *
 * Created by microacup on 2016/11/1.
 */
@Entity
@Table(name = "M_REPLY")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 楼层
    @Column(name = "r_floor", nullable = false)
    private Long floor;

    // 回复内容
    @Column(name = "r_content", nullable = false)
    @Type(type = "text")
    private String content;

    // 所属帖子
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="r_post", nullable=false, updatable = false)
    @JsonBackReference("postReplyReference")
    private Post post;

    // 发布用户
    @ManyToOne
    @JoinColumn(name="r_author", nullable=false, updatable = false)
    private User author;

    // 被回复的回帖
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="r_reply", updatable = false)
    @JsonBackReference
    private Reply reply;

    // 状态
    @Column(name = "r_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    // 创建时间
    @Column(name = "r_createdTime", nullable = false, updatable = false)
    @CreatedDate
    private Date createdTime;

    // 最后更新时间, 默认=createdTime
    @Column(name = "r_updatedTime", nullable = false)
    @LastModifiedDate
    private Date updatedTime;

    // 被删除时间
    @Column(name = "r_deletedTime")
    private Date deletedTime;

    // 是否被采纳
    @Column(name = "r_adopt", nullable = false)
    private Boolean adopt;

    // 被采纳时间
    @Column(name = "r_adoptTime")
    private Date adoptTime;

    // 是否优选
    @Column(name = "r_perfect", nullable = false)
    private Boolean perfect;

    // 优选时间
    @Column(name = "r_perfectTime")
    private Date perfectTime;

    // 是否置顶
    @Column(name = "r_top", nullable = false)
    private Boolean top;

    // 来自... Android客户端...
    @Column(name = "r_from")
    private String from;

    // 置顶时间
    @Column(name = "r_topTime")
    private Date topTime;

    // 赞同
    @Column(name = "r_upCount")
    private Long upCount;

    // 反对
    @Column(name = "r_downCount")
    private Long downCount;

    @Transient
    private Long postId;

    @Transient
    private Long replyId;

    private void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getPostId() {
        return this.postId != null ? this.postId : this.post.getId();
    }

    private void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getReplyId() {
        return this.replyId != null ? this.replyId : this.reply.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Reply) {
            return this.id == ((Reply) obj).getId();
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        }
        return super.hashCode();
    }
}
