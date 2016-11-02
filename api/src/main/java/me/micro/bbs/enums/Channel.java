package me.micro.bbs.enums;

/**
 * User 来源渠道
 *
 * Created by SUNX on 2016/10/14.
 */
public enum Channel {
    NATIVE("本地注册"),
    OPEN("第三方注册"),
    ADMIN("管理员增加");

    private String title;

    Channel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
        public String toString() {
            return getTitle();
    }
}
