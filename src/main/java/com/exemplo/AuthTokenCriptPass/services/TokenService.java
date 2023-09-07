package com.exemplo.AuthTokenCriptPass.services;

//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZoneOffset;
import java.util.Date;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.exemplo.AuthTokenCriptPass.entities.Usuario;

@Service
public class TokenService {
	
	
	
	public static final int TOKEN_EXPIRATION = 600_000;
	//public static final int TOKEN_EXPIRATION = 30;
	
	public static String gerarToken(Usuario usuario ) {
		return JWT.create()
				.withIssuer("Produtos")
				.withSubject(usuario.getUsername())
				.withClaim("id", usuario.getId())
				.withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION))
				//.withExpiresAt(genExpirationDate())
				/*
				.withExpiresAt(LocalDateTime.now()
						.plusMinutes(10)
						.toInstant(ZoneOffset.of("-03:00"))
				)*/
				.sign(Algorithm.HMAC256("chaveSecreta"));
	}
	
	
	/*
	private LocalTime genExpirationDate(){
        //return LocalDateTime.now().plusHours(2).toLocalTime();
        return LocalDateTime.now().plusMinutes(30).toLocalTime();
        
    }
	*/

	public Object getSubject(String token) {
		// TODO Auto-generated method stub
		//return null;
		return JWT.require(Algorithm.HMAC256("chaveSecreta"))
				.withIssuer("Produtos")
				.build().verify(token).getSubject();
	}
	

}
