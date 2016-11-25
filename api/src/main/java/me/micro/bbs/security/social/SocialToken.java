/*
 * @(#)ThirdpayToken.java  2016年9月22日	
 *
 * Copyright  2016 HXDD Corporation Copyright (c) All rights reserved.
 */

package me.micro.bbs.security.social;

import me.micro.bbs.user.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 第三方认证
 * 
 * @author: SUNX
 * @version: 2016年9月22日
 */
public class SocialToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -1681441898112128846L;

    private User credentials;

    public SocialToken(User credentials) {
        super(null);
        this.credentials = credentials;
        super.setAuthenticated(false);
    }

    /**
     * @param authorities
     * @param credentials
     */
    public SocialToken(Collection<? extends GrantedAuthority> authorities, User credentials) {
        super(authorities);
        this.credentials = credentials;
        super.setAuthenticated(true);
    }



    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return credentials;
    }
    
    
    
}
