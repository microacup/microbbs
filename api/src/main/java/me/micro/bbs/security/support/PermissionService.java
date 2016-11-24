package me.micro.bbs.security.support;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.micro.bbs.security.Permission;
import me.micro.bbs.user.User;
import me.micro.bbs.user.support.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * PermissionService
 *
 * Created by microacup on 2016/11/23.
 */
@Service
public class PermissionService implements InitializingBean {
    public static final String CACHE_NAME = "cache.permission";
    public static final String CACHES_NAME = "cache.permissions";
    public static final Class<?> CACHE_TYPE = Permission.class;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserService userService;

    private LoadingCache<String, List<Permission>> cache;

    // 使用Guava Cache代替，避免重复序列化
    // @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Permission> findAll() throws ExecutionException {
        return cache.get(CACHES_NAME);
    }

    public Set<Permission> findByUserId(Long userId) {
        User user = userService.findOne(userId);
        Set<Permission> permissions = new HashSet<>();
        if (user.getRoles().size() > 0) {
            user.getRoles().stream().filter(role -> role.getPermissions().size() > 0).forEach(role -> {
                permissions.addAll(role.getPermissions().stream().collect(Collectors.toSet()));
            });
        }
        return permissions;
    }

    public void save(Permission permission) {
        permissionRepository.save(permission);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<String, List<Permission>>() {
                    @Override
                    public List<Permission> load(String key) throws Exception {
                        return permissionRepository.findAll();
                    }
                });
    }


}
