package me.micro.bbs.consts;

/**
 * URL常量
 *
 * Created by microacup on 2016/10/24.
 */
public final class Uris {
    public static final String API_USERS = "/api/users";

    /** 帖子列表（按N标签过滤）*/
    public static final String API_POSTS = "/api/posts";

    /** 此刻 */
    public static final String API_POSTS_NOW = "/api/posts/now";

    /** 所有分类 */
    public static final String API_CATEGORIES = "/api/categories";

    /**按分类找标签*/
    public static final String API_CATEGORIES_TAGS = "/api/categories/{categoryId}/tags";

    /**按分类找帖子*/
    public static final String API_CATEGORIES_POSTS = "/api/categories/{categoryId}/posts";

    public static final String API_TEST = "/api/test";

    public static final String ID = "/{id}";
    public static final String GENERATE_204 = "/generate_204";
    public static final String AVAILABLE = "/available";

    public static final String ADMIN = "/admin";
    public static final String AUTH_SUCCESS = "/auth/success";

    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";

}
