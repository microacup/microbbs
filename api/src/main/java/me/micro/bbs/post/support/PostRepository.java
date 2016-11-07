package me.micro.bbs.post.support;

import me.micro.bbs.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 帖子
 *
 * Created by microacup on 2016/11/2.
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p INNER JOIN p.tags t WHERE t.id in :tags")
    Page<Post> findByTags(@Param("tags") Collection<Long> tags, Pageable pageable);

    @Query("select distinct p from Post p inner join  p.tags t where t.category.id = :category")
    Page<Post> findByCategoryId(@Param("category") Long category, Pageable pageable);

    Page<Post> findByPerfectTrueOrderByPerfectTimeDesc(Pageable pageable);
}
