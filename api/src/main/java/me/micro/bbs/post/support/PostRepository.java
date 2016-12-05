package me.micro.bbs.post.support;

import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.post.Post;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
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

    @Query("select distinct p from Post p INNER JOIN p.tags t WHERE t.code in :tags")
    Page<Post> findByTags(@Param("tags") Collection<String> tags, Pageable pageable);

    @Query("select distinct p from Post p INNER JOIN p.tags t WHERE t.code in :tags and p.status = :status")
    Page<Post> findByTagsAndStatus(@Param("tags") Collection<String> tags, @Param("status")PostStatus status, Pageable pageable);

    @Query("select distinct p from Post p inner join  p.tags t where t.category.code = :category and p.status = :status")
    Page<Post> findByCategoryAndStatus(@Param("category") String category, @Param("status")PostStatus status, Pageable pageable);

    Page<Post> findByStatusAndPerfectTrue(PostStatus status, Pageable pageable);

    List<Post> findTop5ByStatusOrderByLastTimeDesc(PostStatus status);

    List<Post> findTop5DistinctByIdNotAndTagsInAndStatusOrderByLastTimeDesc(Long id, Collection<Tag> tags, PostStatus status);

    // 某标签下的话题数量
    @Query(value = "select count(1) from m_posts_tags where tag_id = :tagId", nativeQuery = true)
    Long countByTagId(@Param("tagId") Long tagId);

    @Query("select p from Post p where p.author.id = :authorId and p.status = me.micro.bbs.enums.PostStatus.actived")
    Page<Post> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    @Modifying
    @Query("update Post p set p.status = :status, p.deletedTime = :now  where p.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") PostStatus status, @Param("now")Date now);

}
