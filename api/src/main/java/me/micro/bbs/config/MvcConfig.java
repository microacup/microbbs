package me.micro.bbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;

/**
 * MvcConfig
 *
 * Created by microacup on 2016/11/1.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/explore");
        registry.addViewController("/admin").setViewName("admin/index");
        registry.addViewController("/404").setViewName("site/404");

    }

    @Bean(name = {"uih", "viewRenderingHelper"})
    @Scope("request")
    public ViewRenderingHelper viewRenderingHelper() {
        return new ViewRenderingHelper();
    }

    static class ViewRenderingHelper {

        private HttpServletRequest request;

        @Autowired
        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

        public String navClass(String active, String current) {
            if (active.equals(current)) {
                return "active";
            }
            else {
                return "";
            }
        }

        public String tagClass(String active, String current) {
            if (active.equals(current)) {
                return "active";
            }
            else {
                return "";
            }
        }

    }

}
