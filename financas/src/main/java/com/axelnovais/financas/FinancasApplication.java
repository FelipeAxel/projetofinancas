package com.axelnovais.financas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class FinancasApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FinancasApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){

		List<String> all = Arrays.asList("*");

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedMethods(all);
		config.setAllowedOriginPatterns(all);
		config.setAllowedHeaders(all);
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",  config);

		CorsFilter corFilter = new CorsFilter(source);

		FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<CorsFilter>(corFilter);
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return filter;

	}

}
