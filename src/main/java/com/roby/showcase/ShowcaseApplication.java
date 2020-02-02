package com.roby.showcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class ShowcaseApplication extends SpringBootServletInitializer {
	
	/**
	 * Builder method for .war to deploy on the application server 
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ShowcaseApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(ShowcaseApplication.class, args);
	}

}
