package me.micro.bbs.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户资料
 *
 * Created by microacup on 2016/11/22.
 */
@Entity
@Table(name = "SYS_USERPROFILE")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter

public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long userId;

    // 发表话题数
    @Column(nullable = false)
    private Long postCount;

    // 发表回复数
    @Column(nullable = false)
    private Long replyCount;

    // 关注数
    @Column(nullable = false)
    private Long followCount;

    // 粉丝数
    @Column(nullable = false)
    private Long fansCount;

    // 收藏帖子数
    @Column(nullable = false)
    private Long favorCount;

    // 关注标签数
    @Column(nullable = false)
    private Long tagsCount;

    // 赞同
    @Column(nullable = false)
    private Long upCount;

    // 反对
    @Column(nullable = false)
    private Long downCount;

    //公开帖子列表
    @Column(nullable = false)
    private Boolean pubPost;

    // 公开回复列表
    @Column(nullable = false)
    private Boolean pubReply;

    // 公开活动
    @Column(nullable = false)
    private Boolean pubActive;
}
