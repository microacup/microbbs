package me.micro.bbs.enums;

/**
 * 客户端类型
 *
 * Created by SUNX on 2016/10/14.
 */
public enum ClientType {
    ANDROID("Android"),
    IOS("IOS"),
    WEB("Web"),
    CLIENT("客户端"),
    OTHER("other")
    ;
    private String title;

    ClientType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
