package com.example.studyjwt.Security;

import com.example.studyjwt.MODEL.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

  @Value("${forum.jwt.expiration}")
  private String expiration;

  @Value("${forum.jwt.secret}")
  private String secret;

  public String gerarToken(Authentication authentication){
    //User user = (User) authentication.getPrincipal(); -> está dando erro na hora de converter
    //authentication.getPrincipal().toString() -> retorna o email que veio da requisição

    Date today =  new Date();
    Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration));

    return Jwts.builder()
            .setIssuer("API DO FORUM DA ALURA")
            .setSubject(authentication.getPrincipal().toString())
            .setIssuedAt(today)
            .setExpiration(dateExpiration)
            .signWith(SignatureAlgorithm.HS256,secret)
            .compact();
  }


  public boolean isTokenValido(String token) {
    try
    {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    }
    catch (Exception e)
    {
      return false;
    }
  }

  public String getIdUsuario(String token) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    return claims.getSubject(); //retornando username

  }
}
