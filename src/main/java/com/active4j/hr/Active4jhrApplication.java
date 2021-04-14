package com.active4j.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class})
@EnableScheduling //开启定时任务注解
public class Active4jhrApplication extends SpringBootServletInitializer{

 	public static void main(String[] args) {
		SpringApplication.run(Active4jhrApplication.class, args);
	}

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Active4jhrApplication.class);
	}

//	public MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		//  单个文件大小
//		factory.setMaxFileSize("200MB"); // KB,MB
//		/// 总上传文件大小
//		factory.setMaxRequestSize("500MB");
//		return factory.createMultipartConfig();
//	}
	
}
