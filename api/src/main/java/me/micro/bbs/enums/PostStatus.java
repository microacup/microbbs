package me.micro.bbs.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 话题状态
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

    @Override
    public String toString() {
        return this.title;
    }

    @JsonCreator
    public static PostStatus forTitle(String title) {
        for (PostStatus status : PostStatus.values()) {
            if (status.title.equals(title)) {
                return status;
            }
        }
        return disabled;
    }

    @JsonValue
    public String toValue() {
        return this.title;
    }
}
