package com.dva.springboot.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	
	private final Logger log =  LoggerFactory.getLogger(getClass());
	//@Override
	//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//		// TODO Auto-generated method stub
	//WebMvcConfigurer.super.addResourceHandlers(registry);
	//String routPath = Paths.get("uploads").toUri().toString();
	//log.error(routPath);
	//registry.addResourceHandler("/uploads/**")
	//.addResourceLocations(routPath);
	//}
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
}
