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
    public final static String CACHE_CODE = "oauth.cache.code";
    public final static String CACHE_ACCESSTOKEN = "oauth.cache.code";

    public static final Long cacheTimeToLiveCode = 600L; // s
    public static final Long cacheTimeToLiveToken = 60 * 60 * 24 * 30L; // s

    private Cache codeCache;
    private Cache tokenCache;

    @Autowired
    public OAuth2Service(CacheManager cacheManager) {
        this.codeCache = cacheManager.getCache(CACHE_CODE);
        this.tokenCache = cacheManager.getCache(CACHE_ACCESSTOKEN);
    }

    public void addCode(String code, String username) {
        codeCache.put(code, username);
    }

    public String getOpenId(String code) {
        Cache.ValueWrapper valueWrapper = codeCache.get(code);
        if (valueWrapper == null) return null;

        return (String) valueWrapper.get();
    }

    public void addAccessToken(String accessToken, String username) {
        tokenCache.put(accessToken, username);
    }

    public long getExpireIn() {
        return cacheTimeToLiveToken;
    }

    // code 只能使用一次
    public void evict(String code) {
        codeCache.evict(code);
    }
}
