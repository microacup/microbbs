package me.micro.bbs.user;

import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.security.Permission;
import me.micro.bbs.security.oauth.AppInfo;

import java.util.Date;
import java.util.List;

/**
 * 账号授权信息
 *
 * Created by microacup on 2016/11/28.
 */
@Setter
@Getter
public class AuthorizeInfo {
    private Long id;

    // 所属用户
    private User user;

    // app info
    private AppInfo appInfo;

    // 上次授权时间
    private Date lastUsed;

    // 权限
    private List<Permission> permissions;

}
