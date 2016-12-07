package me.micro.bbs.security.support;

import me.micro.bbs.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * RoleService
 *
 * Created by microacup on 2016/11/23.
 */
@Service
public class RoleService {
    public static final String CACHE_NAME = "cache.role";
    public static final Class<?> CACHE_TYPE = Role.class;

    public static final String BUILDIN_ADMIN = "ROLE_ADMIN";
    public static final String BUILDIN_USER = "ROLE_USER";

    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> findByUser(Long userId) {
        return roleRepository.findByUser(userId);
    }

    public void deleteById(Long id) {
        roleRepository.delete(id);
    }

    @Cacheable(value = CACHE_NAME, key = "#code")
    public Role findByCode(String code) {
        return roleRepository.findByCode(code);
    }

    @CacheEvict(value = CACHE_NAME, key = "#role.code")
    public void save(Role role) {
        roleRepository.save(role);
    }
}
