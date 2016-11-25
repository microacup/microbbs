package me.micro.bbs.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * Created by microacup on 2016/11/23.
 */
@Entity
@Table(name = "SYS_ROLE")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode(exclude = {"users","permissions","title","code"}, doNotUseGetters = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    // 角色名称
    @Column(nullable = false, unique = true)
    private String code;

    // 角色描述
    @Column(nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "roles" ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(
            name="sys_role_permission",
            joinColumns={@JoinColumn(name="role_id")},
            inverseJoinColumns={@JoinColumn(name="permission_id")}
    )
    @JsonBackReference("permissionsReference")
    private Set<Permission> permissions = new HashSet<>();

}
