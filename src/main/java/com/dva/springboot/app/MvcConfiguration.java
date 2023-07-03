package com.dva.springboot.app;

import java.nio.file.Paths;
import java.util.Locale;

import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
	
	@Bean
	public static  BCryptPasswordEncoder bcRyptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(new Locale("es", "MX"));
		return  localResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	
	
	
	
	
	
}
