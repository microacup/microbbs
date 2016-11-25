package me.micro.bbs.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限类(白名单模式)
 *
 * Created by microacup on 2016/11/23.
 */
@Entity
@Table(name = "SYS_PERMISSION")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode(exclude = {"roles","url","title","code"}, doNotUseGetters = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Permission implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    //权限名称
    @Column(nullable = false, unique = true)
    private String code;

    //权限描述
    @Column(nullable = false, unique = true)
    private String title;

    //授权链接
    @Column(nullable = false, unique = true)
    private String url;

    // 父节点
    private Long parentId;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
