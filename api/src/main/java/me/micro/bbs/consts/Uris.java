package me.micro.bbs.consts;

/**
 * URL常量
 *
 * Created by microacup on 2016/10/24.
 */
public final class Uris {
    public static final String API_USERS = "/api/users";
    public static final String API_POSTS = "/api/posts";
    public static final String API_CATEGORIES = "/api/categories";
    public static final String API_CATEGORIES_TAGS = "/api/categories/{categoryId}/tags";
    public static final String API_CATEGORIES_POSTS = "/api/categories/{categoryId}/posts";

    public static final String API_TEST = "/api/test";

    public static final String ID = "/{id}";
    public static final String GENERATE_204 = "/generate_204";
    public static final String AVAILABLE = "/available";

    public static final String ADMIN = "/admin";
    public static final String AUTH_SUCCESS = "/auth/success";

    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";

}
