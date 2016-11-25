package me.micro.bbs.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Social Account
 *
 * Created by microacup on 2016/11/24.
 */
@Entity
@Table(name = "SYS_USERSOCIAL", indexes = {@Index(name = "IDX_1", columnList = "source, openid", unique = true)})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserSocial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId", nullable=false, updatable = false)
    @JsonBackReference("userSocialRefrence")
    private User user;

    @Column(nullable = false)
    private String openid; // XXX beta

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @CreatedDate
    private Date createTime;



}
