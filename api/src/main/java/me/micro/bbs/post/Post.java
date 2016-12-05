package me.micro.bbs.post;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.user.User;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 话题
 *
 * 默认顺序（热门、此刻、优选除外）：topTime > lastTime
 * 此刻顺序：lastTime
 * 优选顺序：perfect == true && perfectTime > lastTime
 * 热门顺序：topTime > replyCount > lastTime
 *
 * Created by microacup on 2016/11/1.
 */
@Entity
@Table(name = "M_POST", indexes = {@Index(name = "IDX_STATUS", columnList = "p_status")})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 标题
    @Column(name = "p_title", nullable = false, length = 255)
    private String title;

    // 原始内容,不缓存
    @Column(name = "p_content", nullable = false)
    @Type(type = "text")
    @JsonIgnore
    private String content;

    // 解析后的内容
    @Column(name = "p_renderedContent", nullable = false)
    @Type(type = "text")
    private String renderedContent;

    // 摘要
    @Column(name = "p_summary")
    @Type(type = "text")
    private String summary;

    // 标签
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "M_POSTS_TAGS",
        joinColumns = {@JoinColumn(name = "post_id", nullable = false, updatable = false)},
        inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, updatable = false)})
    @OrderBy("id")
    private List<Tag> tags;

    // 作者
    @ManyToOne
    @JoinColumn(name="p_author", nullable=false, updatable = false)
    @CreatedBy
    private User author;

    // 最后回复人ID
    @Column(name="p_lastAuthor")
    private Long lastAuthor;

    // 最后回复人姓名
    @Column(name = "p_lastAuthorName")
    private String lastAuthorName;

    // 回复数量(实际，不含被删除)
    @Column(name = "p_replyCount", nullable = false)
    private Long replyCount = 0L;

    // 楼层高度（含被删除）
    @Column(name = "p_floorCount", nullable = false)
    private Long floorCount = 0L;

    // 阅读次数
    @Column(name = "p_readCount", nullable = false)
    private Long readCount = 0L;

    // 创建时间
    @Column(name = "p_createdTime", nullable = false, updatable = false)
    @CreatedDate
    private Date createdTime;

    // 更新时间, 默认=createdTime
    @Column(name = "p_updatedTime", nullable = false)
    private Date updatedTime;

    // 最后更新/回复时间，默认=createdTime
    @Column(name = "p_lastTime", nullable = false)
    @LastModifiedDate
    private Date lastTime;

    // 被删除时间
    @Column(name = "p_deletedTime")
    private Date deletedTime;

    // 最后回复时间
    @Column(name = "p_lastReplyTime")
    private Date lastReplyTime;

    // 是否允许评论
    @Column(name = "p_replyable", nullable = false)
    private Boolean replyable;

    // 状态
    @Column(name = "p_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    // 是否优选
    @Column(name = "p_perfect", nullable = false)
    private Boolean perfect = false;

    // 优选时间
    @Column(name = "p_perfectTime")
    private Date perfectTime;

    // 是否置顶
    @Column(name = "p_top", nullable = false)
    private Boolean top = false;

    // 置顶时间
    @Column(name = "p_topTime")
    private Date topTime;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Post) {
            return this.id == ((Post) obj).getId();
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

