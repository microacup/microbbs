package me.micro.bbs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

/**
 * 权限认证设置
 *
 * Created by SUNX on 2016/10/13.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureHeaders(http.headers());
        http.authorizeRequests()
                .antMatchers("/","/static/**", "/test/**").permitAll()
                .anyRequest().authenticated()
                .and().rememberMe()
                .and().formLogin()
                .and().logout()
                .and().csrf().disable();
    }

    private static void configureHeaders(HeadersConfigurer<?> headers) throws Exception {
        HstsHeaderWriter writer = new HstsHeaderWriter(false);
        writer.setRequestMatcher(AnyRequestMatcher.INSTANCE);
        headers.contentTypeOptions().and().xssProtection()
                .and().cacheControl()
                .and().addHeaderWriter(writer).frameOptions();
    }

}
