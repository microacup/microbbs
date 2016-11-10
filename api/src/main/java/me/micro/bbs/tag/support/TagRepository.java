package me.micro.bbs.tag.support;

import me.micro.bbs.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * TagRepository
 *
 * Created by microacup on 2016/11/3.
 */
public interface TagRepository extends JpaRepository<Tag, Long>{

    @Query(value = "select t from Tag t where t.category.id = :categoryId order by t.id")
        // nativeQuery时必须返回所有列§3.10.16.1 "Returning Managed Entities from Native Queries" of the JPA spec says:
        // @Query(value = "select * from M_TAG t where t.t_category = :categoryId", nativeQuery = true)
    List<Tag> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT t.* from m_posts_tags pt, m_tag t where pt.tag_id = t.id GROUP BY tag_id ORDER BY COUNT(pt.tag_id) DESC LIMIT :n",
            nativeQuery = true)
    List<Tag> findTopN(@Param("n") int n);

    @Query(value = "select t.* from m_tag t ,m_posts_tags pt where t.id = pt.tag_id and pt.post_id = :postId", nativeQuery = true)
    List<Tag> findByPostId(@Param("postId") Long postId);

}
