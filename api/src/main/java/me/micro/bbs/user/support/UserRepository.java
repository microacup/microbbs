package me.micro.bbs.user.support;

import me.micro.bbs.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserRepository
 *
 * Created by microacup on 2016/11/11.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByNick(String nick);

    int countByUsernameOrNick(String username, String nick);
}
