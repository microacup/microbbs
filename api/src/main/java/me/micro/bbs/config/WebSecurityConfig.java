package me.micro.bbs.config;

import me.micro.bbs.security.runtime.MyFilterSecurityInterceptor;
import me.micro.bbs.security.social.SocialAuthenticationProvider;
import me.micro.bbs.user.support.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .antMatchers("/","/static/**", "/test/**","/500", "/404", "/oauth/**","/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and().rememberMe().rememberMeServices(rememberMeServices())
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();

        http.csrf().ignoringAntMatchers("/oauth2/**", "/api/**");
    }

    private static void configureHeaders(HeadersConfigurer<?> headers) throws Exception {
        HstsHeaderWriter writer = new HstsHeaderWriter(false);
        writer.setRequestMatcher(AnyRequestMatcher.INSTANCE);
        headers.contentTypeOptions().and().xssProtection()
                .and().cacheControl()
                .and().frameOptions().sameOrigin()
                .addHeaderWriter(writer);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(socialAuthenticationProvider());
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public AuthenticationProvider socialAuthenticationProvider() {
        return new SocialAuthenticationProvider();
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("remember-me-key", userService());
    }

    @Override
    @Bean(name="authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public MyFilterSecurityInterceptor myFilterSecurityInterceptor() {
        return new MyFilterSecurityInterceptor();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
