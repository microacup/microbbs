package me.micro.bbs.user.support;

import me.micro.bbs.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户资料
 *
 * Created by microacup on 2016/11/22.
 */
@Repository
@Transactional
public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserId(Long userId);

    @Modifying
    @Query("update UserProfile up set up.postCount = up.postCount + 1 where up.userId = :userId")
    int addPostCount(@Param("userId") Long userId);

    @Modifying
    @Query("update UserProfile up set up.replyCount = up.replyCount + 1 where up.userId = :userId")
    int addReplyCount(@Param("userId") Long userId);
}
