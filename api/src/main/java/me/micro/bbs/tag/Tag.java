package me.micro.bbs.tag;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.category.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 标签
 *
 * Created by microacup on 2016/11/1.
 */
@Entity
@Table(name = "M_TAG")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "t_title", nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_category")
    private Category category;

    /*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonBackReference(value = "postReference")
    private List<Post> posts;*/
}
