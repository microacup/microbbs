package me.micro.bbs.security.support;

import me.micro.bbs.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * Created by microacup on 2016/11/23.
 */
@Transactional
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
