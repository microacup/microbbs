package me.micro.bbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
        registry.addViewController("/").setViewName("redirect:/hot");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/explore").setViewName("forward:/hot");
        registry.addViewController("/admin").setViewName("admin/index");
        registry.addViewController("/404").setViewName("site/404");
        registry.addViewController("/500").setViewName("site/500");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600*24)
                .resourceChain(true);
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

        public String adminNavClass(String active, String current) {
            if (active.equals(current)) {
                return "treeview active";
            }
            else {
                return "treeview";
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
