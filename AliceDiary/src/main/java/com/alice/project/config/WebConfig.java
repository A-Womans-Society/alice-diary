package com.alice.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//외부경로에 있는 리소스를 접근할 수 있는 방법
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/member/**")
				.addResourceLocations("file:///C:/Temp/upload/");
	}

}
