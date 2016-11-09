package me.micro.bbs.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "t_code", unique = true, nullable = false, length = 255)
    private String code;

    @Column(name = "t_title", nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_category")
    @JsonIgnore
    private Category category;

    @Transient
    private Long categoryId;

    public Category getCategory() {
        throw new UnsupportedOperationException("请调用getCategoryId");
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        if (this.category != null) {
            return this.category.getId();
        }

        return categoryId;
    }

    /*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonBackReference(value = "postReference")
    private List<Post> posts;*/

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Tag) {
            return this.id == ((Tag) obj).getId();
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        }
        return super.hashCode();
    }
}
