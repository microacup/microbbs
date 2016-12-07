package me.micro.bbs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MicrobbsApplication extends SpringBootServletInitializer {
	@Value("${server.port}") private String port;
	@Value("${server.contextPath}") private String contextPath;

	public static void main(String[] args) {
		SpringApplication.run(MicrobbsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MicrobbsApplication.class);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return (container -> {
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/404");
			ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

			container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
		});
	}

	@PostConstruct
	public void startup() {
		System.out.println("启动完成，请访问 http://localhost:" + port + contextPath);
	}
}
