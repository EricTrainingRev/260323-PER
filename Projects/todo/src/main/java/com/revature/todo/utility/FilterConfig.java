package com.revature.todo.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter authFilter) {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/todo/*");
		registrationBean.setOrder(1); // Set priority if needed
		return registrationBean;
	}
}
