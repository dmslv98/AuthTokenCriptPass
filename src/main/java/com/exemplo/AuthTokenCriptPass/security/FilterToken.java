package com.exemplo.AuthTokenCriptPass.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exemplo.AuthTokenCriptPass.Repositories.UsuarioRepository;
import com.exemplo.AuthTokenCriptPass.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)  throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String token;
		
		var authorizationHeader = request.getHeader("Authorization");
		
		
		if (authorizationHeader != null) {
			token = authorizationHeader.replace("Bearer ","");
			var subject = this.tokenService.getSubject(token);
			
			var usuario = this.usuarioRepository.findByLogin(subject);
			
			var authentication = new UsernamePasswordAuthenticationToken(
					usuario,null,usuario.getAuthorities());
			
			
			SecurityContextHolder.getContext().setAuthentication(authentication);			
		}
		
		filterChain.doFilter(request, response);
		
		
		
		
	}

}
