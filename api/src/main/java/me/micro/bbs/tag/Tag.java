package me.micro.bbs.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.category.Category;

import javax.persistence.*;

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
    @JoinTable(name = "M_TAGS_CATES",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns={@JoinColumn(name = "cate_id")})
    @JsonBackReference(value = "categoryReference")
    private Category category;

    /*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonBackReference(value = "postReference")
    private List<Post> posts;*/
}
