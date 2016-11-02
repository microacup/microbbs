package me.micro.bbs.cache;

import com.fasterxml.jackson.databind.JavaType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自定义的JsonRedisTemplate
 *
 * JsonRedisTemplate
 */
public class JsonRedisTemplate<V> extends RedisTemplate<String, V> {

    public JsonRedisTemplate(RedisConnectionFactory connectionFactory, Class<V> valueType) {
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(valueType);
        init(connectionFactory, jsonRedisSerializer);
    }

    public JsonRedisTemplate(RedisConnectionFactory connectionFactory, JavaType valueType) {
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(valueType);
        init(connectionFactory, jsonRedisSerializer);
    }

    private void init(RedisConnectionFactory connectionFactory, Jackson2JsonRedisSerializer jsonRedisSerializer) {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        setKeySerializer(stringSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(stringSerializer);
        setValueSerializer(jsonRedisSerializer);
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

}
