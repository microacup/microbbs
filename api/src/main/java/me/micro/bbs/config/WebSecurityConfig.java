package me.micro.bbs.config;

import me.micro.bbs.security.runtime.MyFilterSecurityInterceptor;
import me.micro.bbs.user.support.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
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
        http.addFilterBefore(myFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
        http.authorizeRequests()
                .antMatchers("/","/static/**", "/test/**").permitAll()
                .anyRequest().authenticated()
                .and().rememberMe().rememberMeServices(rememberMeServices())
                .and().formLogin().permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
                .and().csrf();
    }

    private static void configureHeaders(HeadersConfigurer<?> headers) throws Exception {
        HstsHeaderWriter writer = new HstsHeaderWriter(false);
        writer.setRequestMatcher(AnyRequestMatcher.INSTANCE);
        headers.contentTypeOptions().and().xssProtection()
                .and().cacheControl()
                .and().frameOptions().sameOrigin()
                .addHeaderWriter(writer).frameOptions();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true)
                .userDetailsService(userService())
                ;
    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }*/

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", userService());
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public MyFilterSecurityInterceptor myFilterSecurityInterceptor() {
        return new MyFilterSecurityInterceptor();
    }

}
