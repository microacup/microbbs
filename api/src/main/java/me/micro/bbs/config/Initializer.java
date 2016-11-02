package me.micro.bbs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * SessionConfig Initializer
 *
 * Created by microacup on 2016/10/28.
 */
@Configuration
public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(SessionConfig.class);
    }
}
