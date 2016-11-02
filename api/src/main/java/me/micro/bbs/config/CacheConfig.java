package me.micro.bbs.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import me.micro.bbs.cache.CacheKeyGenerator;
import me.micro.bbs.cache.JsonRedisTemplate;
import me.micro.bbs.cache.RedisCacheManager;
import me.micro.bbs.post.support.PostService;
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

    protected Long cacheTimeToLive = 300L; // s

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisConnectionFactory);
        TypeFactory typeFactory = TypeFactory.defaultInstance();

        // Post Cache
        JsonRedisTemplate<? extends Object> userTemplete = new JsonRedisTemplate<>(redisConnectionFactory, PostService.CACHE_TYPE);
        cacheManager.withCache(PostService.CACHE_NAME, userTemplete, this.cacheTimeToLive);

        // Post List Cache
        JavaType userList = typeFactory.constructParametricType(ArrayList.class, PostService.CACHE_TYPE);
        JsonRedisTemplate<? extends Object>  usersTemplete = new JsonRedisTemplate<>(redisConnectionFactory, userList);
        cacheManager.withCache(PostService.CACHES_NAME, usersTemplete, this.cacheTimeToLive);

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
