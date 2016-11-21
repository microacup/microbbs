package me.micro.bbs.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.Channel;
import me.micro.bbs.enums.ClientType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

/**
 * 用户
 *
 * Created by microacup on 2016/11/1.
 */
@Entity
@Table(name = "SYS_USER")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class User implements UserDetails {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 内置用户ID
    @Column(name = "u_username", nullable = false, unique = true)
    private String username;

    // 昵称
    @Column(name = "u_nick")
    private String nick;

    // 一句话介绍
    @Column(name = "u_info", length = 255)
    private String info;

    // 密码
    @JsonIgnore
    @Column(name = "u_password",nullable = false)
    private String password;

    // 手机号
    @Column(name = "u_cellphone", unique = true)
    private String cellphone;

    // 电子邮箱
    @Column(name = "u_email", unique = true)
    private String email;

    // 真实姓名
    @Column(name = "u_name")
    private String name;

    @Column(name = "u_gender")
    private int gender;

    // 公司/学校/机构
    @Column(name = "u_company")
    private String company;

    @Column(name = "u_companyType")
    private String companyType;

    // 职位/职称
    @Column(name = "u_title")
    private String title;

    // 省份
    @Column(name = "u_province")
    private String province;

    // 城市
    @Column(name = "u_city")
    private String city;

    // 区县
    @Column(name = "u_area")
    private String area;

    // 地址
    @Column(name = "u_address")
    private String address;

    // 证件类型
    @Column(name = "u_idCardType")
    private String idCardType;

    // 证件编码
    @Column(name = "u_idCard", unique = true)
    private String idCard;

    // 注册时间
    @Column(name = "u_registerTime")
    private Date registerTime;

    // 登录次数
    @Column(name = "u_loginTimes")
    private Integer loginTimes = 0;

    // 最后登录时间
    @Column(name = "u_lastLoginTime")
    private Date lastLoginTime;

    // 头像地址
    @Column(name = "u_avatar")
    private String avatar;

    // 注册来源
    @Column(name = "u_channel", nullable = false)
    @Enumerated(EnumType.STRING)
    private Channel channel;

    // 注册来源
    @Column(name = "u_client", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType client;

    // 账号是否激活
    @Column(name = "u_isActive")
    private Boolean isActive;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.isActive;
    }
}
