package me.micro.bbs;

import me.micro.bbs.file.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
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

	@PostConstruct
	public void startup() {
		System.out.println("启动完成，请访问 http://localhost:" + port + contextPath);
	}
}
