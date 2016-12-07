package me.micro.bbs.security.support;

import me.micro.bbs.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 *
 *
 * Created by microacup on 2016/11/23.
 */
@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByCode(String code);

    @Query(value = "select r.* from sys_role r, sys_user_role ur where ur.role_id = r.id and ur.user_id = :userId", nativeQuery = true)
    Set<Role> findByUser(@Param("userId") Long userId);
}
