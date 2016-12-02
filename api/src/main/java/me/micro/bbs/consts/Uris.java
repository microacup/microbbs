package me.micro.bbs.consts;

/**
 * URL常量
 *
 * Created by microacup on 2016/10/24.
 */
public final class Uris {
    public static final String API_USERS = "/api/users";

    /** 话题列表（按N标签过滤）*/
    public static final String API_POSTS = "/api/posts";

    /** 此刻 */
    public static final String API_POSTS_NOW = "/api/posts/now";

    /** 我的帖子 */
    public static final String API_POSTS_ME = "/api/posts/me";

    /** 前5名新话题 */
    public static final String API_POSTS_NOW_TOP5 = "/api/posts/now/top5";

    /** 前5名相关话题 */
    public static final String API_POSTS_RELATED_TOP5 = "/api/posts/{postId}/related/top5";

    /** 所有分类 */
    public static final String API_CATEGORIES = "/api/categories";

    /** 按分类找标签*/
    public static final String API_CATEGORIES_TAGS = "/api/categories/{code}/tags";

    /** 热门标签 */
    public static final String API_HOT_TAGS = "/api/tags/hot";

    /** 按分类找话题*/
    public static final String API_CATEGORIES_POSTS = "/api/categories/{category}/posts";

    /** 按照话题找回复*/
    public static final String API_POSTS_ID_REPLIES = "/api/posts/{postId}/replies";

    // 回复
    public static final String API_REPLIES = "/api/replies";

    // 回复的回复
    public static final String API_REPLIES_REPLY_ID_REPLIES = "/api/replies/{replyId}/replies";

    // 消息
    public static final String API_MESSAGES = "/api/messages";

    // 个人信息
    public static final String API_PROFILE = "/api/profile";
    public static final String API_AVATAR = "/api/avatar";
    public static final String API_USERS_USER_ID_PROFILE = "/api/users/{userId}/profile";
    public static final String API_USERS_USER_ID_AVATAR = "/api/users/{userId}/avatar";
    public static final String API_USERS_USER_ID_POSTS = "/api/users/{userId}/posts";

    ////////////////////////////admin////////////////////////////////
    public static final String API_ADMIN_POSTS = "/api/admin/posts";

    /** 所有分类*/
    public static final String API_ADMIN_CATEGORIES = "/api/admin/categories";

    /** 所有标签*/
    public static final String API_ADMIN_TAGS = "/api/admin/tags";

    /** 按照话题找回复*/
    public static final String API_ADMIN_POSTS_ID_REPLIES = "/api/admin/posts/{postId}/replies";

    // 测试API
    public static final String API_TEST = "/api/test";

    public static final String ID = "/{id}";
    public static final String GENERATE_204 = "/generate_204";
    public static final String AVAILABLE = "/available";

    public static final String ADMIN = "/admin";
    public static final String AUTH_SUCCESS = "/auth/success";

    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";

}
