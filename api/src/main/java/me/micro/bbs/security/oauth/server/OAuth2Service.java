package me.micro.bbs.security.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * OAuth2Service
 *
 * Created by microacup on 2016/11/28.
 */
@Service
public class OAuth2Service {
    public final static String CACHE_NAME = "oauth.cache";

    private Cache cache;

    @Autowired
    public OAuth2Service(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(CACHE_NAME);
    }

    public void addCode(String code, String username) {
        cache.put(code, username);
    }



}
