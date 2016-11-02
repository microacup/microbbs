package me.micro.bbs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * SecurityInitializer
 *
 * Created by microacup on 2016/10/31.
 */
@Configuration
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(WebSecurityConfig.class, SessionConfig.class);
    }

}
