package com.roniantonius.barang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.roniantonius.barang.domain.entities.User;
import com.roniantonius.barang.repositories.UserRepository;
import com.roniantonius.barang.security.BarangUserDetailsService;
import com.roniantonius.barang.security.JwtAuthenticationFilter;
import com.roniantonius.barang.services.AuthenticationService;

import jakarta.servlet.Filter;

@Configuration
public class SecurityConfig {
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
		return new JwtAuthenticationFilter(authenticationService);
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		BarangUserDetailsService barangUserDetailsService = new BarangUserDetailsService(userRepository);
		
		String emailTes = "penggunaBarang@test.com";
		userRepository.findByEmail(emailTes).orElseGet(() -> {
			User user = User.builder()
					.name("Test pengguna")
					.email(emailTes)
					.password(passwordEncoder().encode("password"))
					.build();
			return userRepository.save(user);
		});
		
		return barangUserDetailsService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/posts/drafts").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
						.anyRequest().authenticated()
						)
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationToken.class);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
}
