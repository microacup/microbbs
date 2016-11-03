package me.micro.bbs.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分类
 *
 * Created by microacup on 2016/11/1.
 */
@Entity
@Table(name = "M_CATEGORY")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_title", nullable = false, length = 255)
    private String title;

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;*/
}
