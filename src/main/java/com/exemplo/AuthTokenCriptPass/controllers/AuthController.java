package com.exemplo.AuthTokenCriptPass.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.AuthTokenCriptPass.Repositories.ProdutoRepository;
import com.exemplo.AuthTokenCriptPass.Repositories.UsuarioRepository;
import com.exemplo.AuthTokenCriptPass.dto.Login;
import com.exemplo.AuthTokenCriptPass.entities.Produto;
import com.exemplo.AuthTokenCriptPass.entities.Usuario;
import com.exemplo.AuthTokenCriptPass.services.TokenService;



@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@GetMapping("/listarUser")
	public ResponseEntity<List<Usuario>> listarUser(){		
		return ResponseEntity.ok(usuarioRepository.findAll());				
	}
	
	@GetMapping("/listarProd")  
	public ResponseEntity<List<Produto>> listarProd(){		
		return ResponseEntity.ok(produtoRepository.findAll());				
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Login login){
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(login.login(), login.password());
		
		Authentication authentication = this.authenticationManager.
				authenticate(usernamePasswordAuthenticationToken);
		
		var usuario = (Usuario) authentication.getPrincipal();
		
		return TokenService.gerarToken(usuario);
	}
		
	
	@PostMapping("/salvarUser")
	public ResponseEntity<Usuario> salvarUser(@RequestBody Usuario usuario){
		
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		
		return ResponseEntity.ok(usuarioRepository.save(usuario));
	}
	
	
	@PostMapping("/salvarProd")
	public ResponseEntity<Produto> salvar(@RequestBody Produto produto){		
		return ResponseEntity.ok(produtoRepository.save(produto));
	}
	
	
	

}
