package me.micro.bbs.file;

/**
 * 文件用途
 *
 * Created by microacup on 2016/11/17.
 */
public enum FilePart {
    avatar("avatar/"),
    post("post/"),
    reply("reply/"),
    common("common/")
    ;

    String path;

    FilePart(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
