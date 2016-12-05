package me.micro.bbs.reply.support;

import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.post.Post;
import me.micro.bbs.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * ReplyRepository
 *
 * Created by microacup on 2016/11/8.
 */
@Repository
@Transactional
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 按照Post查询根据状态过滤（前端）
    Page<Reply> findByPostAndStatus(Post post, PostStatus status, Pageable pageable);

    // 根据状态过滤（Admin）
    Page<Reply> findByPost(Post post, Pageable pageable);

    // 根据回复过滤（前端）
    Page<Reply> findByReplyAndStatus(Reply reply, PostStatus status, Pageable pageable);

    // 根据回复过滤（Admin）
    Page<Reply> findByReply(Reply reply, Pageable pageable);

    @Modifying
    @Query("update Reply r set r.deletedTime = :now , r.status = :status where r.post.id = :postId")
    void updateStatusByPost(@Param("postId") Long postId, @Param("status") PostStatus status, @Param("now") Date now);

}
