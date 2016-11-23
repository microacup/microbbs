package me.micro.bbs.user.support;

import me.micro.bbs.security.Permission;
import me.micro.bbs.security.support.PermissionService;
import me.micro.bbs.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *
 *
 * Created by microacup on 2016/11/21.
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("没有找到该用户");
        }

        // 加载权限
        Set<Permission> permissions = permissionService.findByUserId(user.getId());
        user.setPermissions(permissions);

        return user;
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }
}
