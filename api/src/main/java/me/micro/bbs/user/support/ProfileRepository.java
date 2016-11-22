package me.micro.bbs.user.support;

import me.micro.bbs.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
