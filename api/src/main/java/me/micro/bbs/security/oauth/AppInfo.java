package me.micro.bbs.security.oauth;

import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.ClientType;

/**
 * Oauth App Info
 *
 * Created by microacup on 2016/11/28.
 */
@Getter
@Setter
public class AppInfo {

    private Long id;

    // app名称
    private String appName;

    // 简介
    private String description;

    // app Type
    private ClientType appType;

    // app url
    private String url;

    private String logo;

    private String appKey;

    private String appSecret;
}
