package me.micro.bbs.user;

import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.Channel;
import me.micro.bbs.enums.ClientType;

import javax.validation.constraints.NotNull;

/**
 * UserForm注册信息
 *
 * Created by microacup on 2016/11/24.
 */
@Getter
@Setter
public class UserForm {
    @NotNull
    private String username;

    @NotNull
    private String nick;

    @NotNull
    private String info;

    private String cellphone;

    private String email;

    private String name;

    @NotNull
    private int gender;

    @NotNull
    private Channel channel;

    @NotNull
    private ClientType client;


    // 第三方账号OpendId
    private String openid;

    // 第三方账号来源
    private String source;

    // 第三方账号昵称
    private String openNickname;


}
