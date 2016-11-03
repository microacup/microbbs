package me.micro.bbs.post;


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
import java.util.Set;

/**
 * 帖子
 *
 * Created by microacup on 2016/11/1.
 */
@Entity
@Table(name = "M_POST")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 标题
    @Column(name = "p_title", nullable = false, length = 255)
    private String title;

    // 内容
    @Column(name = "p_content", nullable = false)
    @Type(type = "text")
    private String content;

    // 摘要
    @Column(name = "p_summary", length = 255)
    private String summary;

    // 标签
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "M_POSTS_TAGS",
        joinColumns = {@JoinColumn(name = "post_id", nullable = false, updatable = false)},
        inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, updatable = false)})
    private Set<Tag> tags;

    // 作者
    @ManyToOne
    @JoinColumn(name="p_author", nullable=false)
    @CreatedBy
    private User author;

    // 最后回复人
    @Column(name="p_lastAuthor", nullable=false)
    private Long lastAuthor;

    @Column(name = "p_lastAuthorName")
    private String lastAuthorName;

    // 创建时间
    @Column(name = "p_createdTime", nullable = false)
    @CreatedDate
    private Date createdTime;

    // 最后更新时间
    @Column(name = "p_updatedTime")
    @LastModifiedDate
    private Date updatedTime;

    // 最后回复时间
    @Column(name = "p_lastReplyTime")
    @LastModifiedDate
    private Date lastReplyTime;

    // 是否允许评论
    @Column(name = "p_reply", nullable = false)
    private Boolean reply;

    // 是否删除
    @Column(name = "p_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

}

