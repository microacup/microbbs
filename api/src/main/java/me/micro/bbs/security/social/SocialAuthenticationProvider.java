/*
 * @(#)ThirdpayProvider.java  2016年9月22日	
 *
 * Copyright  2016 HXDD Corporation Copyright (c) All rights reserved.
 */

package me.micro.bbs.security.social;

import me.micro.bbs.user.User;
import me.micro.bbs.user.support.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 第三方登录认证接口
 * 
 * @author: SUNX
 * @version: 2016年9月22日
 */
public class SocialAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialToken token = (SocialToken) authentication;
        User user = (User) authentication.getCredentials();
        token.setAuthenticated(true);

        User userDetails = (User) userDetailsService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authenticationToken.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return authentication;
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return SocialToken.class.isAssignableFrom(authentication);
    }

}
