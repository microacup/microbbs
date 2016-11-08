package me.micro.bbs.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.tag.Tag;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_code", unique = true, nullable = false, length = 255)
    private String code;

    @Column(name = "c_title", nullable = false, length = 255)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Tag> tags;


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Category) {
            return this.id == ((Category) obj).getId();
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
