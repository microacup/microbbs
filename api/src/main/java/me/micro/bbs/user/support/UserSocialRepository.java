package me.micro.bbs.user.support;

import me.micro.bbs.user.User;
import me.micro.bbs.user.UserSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserSocialRepository
 *
 * Created by microacup on 2016/11/25.
 */
@Repository
@Transactional
public interface UserSocialRepository extends JpaRepository<UserSocial, Long> {
    UserSocial findBySourceAndOpenid(String source, String openid);
    UserSocial findByUserAndSource(User user, String source);
}
