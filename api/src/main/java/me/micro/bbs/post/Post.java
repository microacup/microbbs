package me.micro.bbs.post;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 帖子
 *
 * Created by microacup on 2016/11/1.
 */
@Entity
@Table(name = "M_POST")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String summary;

}
