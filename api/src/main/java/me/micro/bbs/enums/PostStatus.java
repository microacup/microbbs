package me.micro.bbs.enums;

/**
 * 帖子状态
 *
 * Created by microacup on 2016/11/2.
 */
public enum PostStatus {
    actived("正常"),
    disabled("黑名单"),
    deleted("删除")
    ;
    private String title;


    PostStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
