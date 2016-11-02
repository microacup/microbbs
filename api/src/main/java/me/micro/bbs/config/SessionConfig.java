package me.micro.bbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Redis Session Config
 *
 * Created by microacup on 2016/10/28.
 */
@EnableRedisHttpSession
public class SessionConfig {

    /*
    // 等待新版本支持JSON ：Security 4.2
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper());
    }

    *//**
     * Customized {@link com.fasterxml.jackson.databind.ObjectMapper} to add mix-in for class that doesn't have default
     * constructors
     *
     * @return the {@link com.fasterxml.jackson.databind.ObjectMapper} to use
     *//*
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }*/

    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        //serializer.setCookieName("JSESSIONID"); // <1>
        serializer.setCookiePath("/"); // <2>
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); // <3>
        return serializer;
    }

}
