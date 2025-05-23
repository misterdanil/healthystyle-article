package org.healthystyle.article.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterCharin(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(req -> req.anyRequest().permitAll()).csrf(csrf -> csrf.disable()).build();
	}
}
