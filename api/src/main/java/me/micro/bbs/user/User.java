package me.micro.bbs.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.Channel;
import me.micro.bbs.enums.ClientType;
import me.micro.bbs.security.Permission;
import me.micro.bbs.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // OpenId。OAuth2提供方
    @Column(name = "u_openId", unique = true)
    private String openId;

    // 内置用户ID
    @Column(name = "u_username", nullable = false, unique = true)
    private String username;

    // 昵称
    @Column(name = "u_nick", unique = true)
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

    @Column(name = "u_gender", nullable = false)
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
    @Column(name = "u_registerTime", nullable = false)
    private Date registerTime;

    // 登录次数
    @Column(name = "u_loginTimes", nullable = false)
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
    @Column(name = "u_isActive", nullable = false)
    private Boolean isActive = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="sys_user_role",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")}
    )
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    // 权限列表
    @Transient
    @JsonIgnore
    private Set<Permission> permissions = new HashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles.isEmpty() && permissions.isEmpty())
            return null;

        List<GrantedAuthority> grantedAuthorities = Lists.newArrayListWithExpectedSize(roles.size() + permissions.size());
        grantedAuthorities.addAll(roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList()));
        grantedAuthorities.addAll(permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getCode())).collect(Collectors.toList()));

        return grantedAuthorities;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
