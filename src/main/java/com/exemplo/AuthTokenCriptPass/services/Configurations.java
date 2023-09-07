package com.exemplo.AuthTokenCriptPass.services;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exemplo.AuthTokenCriptPass.security.FilterToken;

@Configuration
@EnableWebSecurity
public class Configurations {
	
	@Autowired
	private FilterToken filter;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		/*
		return http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests()
				.and()
				.antMatchers(HttpMethod.POST,"/login").permitAll
				.antMatchers(HttpMethod.GET,"/home").permitAll
				.anyRequest().authenticated().and().build();				
		*/
		return  http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/home").permitAll()	                         
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()
                )     
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();		
	}
	
	
	
	@Bean
	public AuthenticationManager  authenticationManager 
	(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		
		return authenticationConfiguration.getAuthenticationManager();		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
