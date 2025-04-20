package com.fulljob.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FullJobApiApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) 
	{ return builder.sources(FullJobApiApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FullJobApiApplication.class, args);
	}

}
