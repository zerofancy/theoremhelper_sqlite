package com.zerofancy.theoremhelper;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zerofancy.theoremhelper.interceptor.AdmInterceptor;

@Configuration

public class MyWebAppConfigurer implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截

		registry.addInterceptor(new AdmInterceptor()).addPathPatterns("/admin/**")
													.excludePathPatterns("/admin/login")
													.excludePathPatterns("/admin/login/**");
		// 可以在此定义多个拦截器
		// registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		AntPathMatcher matcher = new AntPathMatcher();
		matcher.setCaseSensitive(false);// 大小写不敏感
		configurer.setPathMatcher(matcher);
	}
}
