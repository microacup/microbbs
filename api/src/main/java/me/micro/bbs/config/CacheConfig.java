package me.micro.bbs.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import me.micro.bbs.cache.CacheKeyGenerator;
import me.micro.bbs.cache.JsonRedisTemplate;
import me.micro.bbs.cache.RedisCacheManager;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.reply.support.ReplyService;
import me.micro.bbs.security.support.PermissionService;
import me.micro.bbs.security.support.RoleService;
import me.micro.bbs.tag.support.TagService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.ArrayList;

/**
 * Cache Config
 *
 * Created by microacup on 2016/10/17.
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig extends CachingConfigurerSupport {

    private static final Long cacheTimeToLive = 300L; // s

    private static final Long cacheTimeToLiveShort = 60L; // s


    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisConnectionFactory);
        TypeFactory typeFactory = TypeFactory.defaultInstance();

        // Post Cache
        JsonRedisTemplate<? extends Object> postTemplate = new JsonRedisTemplate<>(redisConnectionFactory, PostService.CACHE_TYPE);
        cacheManager.withCache(PostService.CACHE_NAME, postTemplate, cacheTimeToLive);

        // Post List Cache
        JavaType posts = typeFactory.constructParametricType(ArrayList.class, PostService.CACHE_TYPE);
        JsonRedisTemplate<? extends Object>  postsTemplate = new JsonRedisTemplate<>(redisConnectionFactory, posts);
        cacheManager.withCache(PostService.CACHES_NAME, postsTemplate, cacheTimeToLive);

        JsonRedisTemplate<? extends Object>  realtimePosts = new JsonRedisTemplate<>(redisConnectionFactory, posts);
        cacheManager.withCache(PostService.CACHES_REALTIME_NAME, realtimePosts, cacheTimeToLiveShort);

        // Tag Cache
        JsonRedisTemplate<? extends Object> tagTemplate = new JsonRedisTemplate<>(redisConnectionFactory, TagService.CACHE_TYPE);
        cacheManager.withCache(TagService.CACHE_NAME, tagTemplate, cacheTimeToLive);

        // Tag List Cache
        JavaType tags = typeFactory.constructParametricType(ArrayList.class, TagService.CACHE_TYPE);
        JsonRedisTemplate<? extends Object>  tagsTemplate = new JsonRedisTemplate<>(redisConnectionFactory, tags);
        cacheManager.withCache(TagService.CACHES_NAME, tagsTemplate, cacheTimeToLive);

        // Category Cache
        JsonRedisTemplate<? extends Object> cateTemplate = new JsonRedisTemplate<>(redisConnectionFactory, CategoryService.CACHE_TYPE);
        cacheManager.withCache(CategoryService.CACHE_NAME, cateTemplate, cacheTimeToLive);

        // Category List Cache
        JavaType cates = typeFactory.constructParametricType(ArrayList.class, CategoryService.CACHE_TYPE);
        JsonRedisTemplate<? extends Object>  catesTemplate = new JsonRedisTemplate<>(redisConnectionFactory, cates);
        cacheManager.withCache(CategoryService.CACHES_NAME, catesTemplate, cacheTimeToLive);

        // Reply Cache
        JsonRedisTemplate<? extends Object> replyTemplate = new JsonRedisTemplate<>(redisConnectionFactory, ReplyService.CACHE_TYPE);
        cacheManager.withCache(ReplyService.CACHE_NAME, replyTemplate, cacheTimeToLive);

        // Reply List Cache
        JavaType replies = typeFactory.constructParametricType(ArrayList.class, ReplyService.CACHE_TYPE);
        JsonRedisTemplate<? extends Object>  repliesTemplate = new JsonRedisTemplate<>(redisConnectionFactory, replies);
        cacheManager.withCache(ReplyService.CACHES_NAME, repliesTemplate, cacheTimeToLive);

        // Permission
        JavaType permissions = typeFactory.constructCollectionType(ArrayList.class, PermissionService.CACHE_TYPE);
        JsonRedisTemplate<Object> permissionsTemplate = new JsonRedisTemplate<>(redisConnectionFactory, permissions);
        cacheManager.withCache(PermissionService.CACHES_NAME, permissionsTemplate, cacheTimeToLive);

        JsonRedisTemplate<? extends Object> permissionTemplate = new JsonRedisTemplate<>(redisConnectionFactory, PermissionService.CACHE_TYPE);
        cacheManager.withCache(PermissionService.CACHE_NAME, permissionTemplate, cacheTimeToLive);

        // Role
        JsonRedisTemplate<? extends Object> roleTemplte = new JsonRedisTemplate<>(redisConnectionFactory, RoleService.CACHE_TYPE);
        cacheManager.withCache(RoleService.CACHE_NAME, roleTemplte, cacheTimeToLive);

        return cacheManager;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean("cacheKeyGenerator")
    public KeyGenerator cacheKeyGenerator() {
        return new CacheKeyGenerator();
    }
}
