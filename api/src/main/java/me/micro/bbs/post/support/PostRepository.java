package me.micro.bbs.post.support;

import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.post.Post;
import me.micro.bbs.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 话题
 *
 * Created by microacup on 2016/11/2.
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByStatus(PostStatus status, Pageable pageable);

    @Query("select distinct p from Post p INNER JOIN p.tags t WHERE t.id in :tags")
    Page<Post> findByTags(@Param("tags") Collection<Long> tags, Pageable pageable);

    @Query("select distinct p from Post p INNER JOIN p.tags t WHERE t.id in :tags and p.status = :status")
    Page<Post> findByTagsAndStatus(@Param("tags") Collection<Long> tags, @Param("status")PostStatus status, Pageable pageable);

    @Query("select distinct p from Post p inner join  p.tags t where t.category.id = :category and p.status = :status")
    Page<Post> findByCategoryIdAndStatus(@Param("category") Long category, @Param("status")PostStatus status, Pageable pageable);

    Page<Post> findByPerfectTrue(Pageable pageable);

    List<Post> findTop5ByOrderByLastTimeDesc();

    List<Post> findTop5DistinctByIdNotAndTagsInOrderByLastTimeDesc(Long id, Collection<Tag> tags);

    // 某标签下的话题数量
    @Query(value = "select count(1) from m_posts_tags where tag_id = :tagId", nativeQuery = true)
    Long countByTagId(@Param("tagId") Long tagId);

}
