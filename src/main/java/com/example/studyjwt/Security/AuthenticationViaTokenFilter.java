package com.example.studyjwt.Security;

import com.example.studyjwt.DAO.UserDAO;
import com.example.studyjwt.MODEL.User;
import com.example.studyjwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationViaTokenFilter extends OncePerRequestFilter {

  private TokenService tokenService; //nas classes de filter, não conseguir usar o @Autowired
  private UserDAO userDAO;
  public AuthenticationViaTokenFilter(TokenService tokenService,UserDAO userDAO) {
    this.tokenService = tokenService;
    this.userDAO = userDAO;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String token = recuperaToken(request); //pegando o token do cabeçalho
    boolean tokenValidado = tokenService.isTokenValido(token); //validando o token
    if(tokenValidado){
      autenticarCliente(token);
    }

    filterChain.doFilter(request,response);
  }

  private void autenticarCliente(String token){

    String username = tokenService.getIdUsuario(token);
    User usuario = userDAO.getUserByName(username).get();
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String recuperaToken(HttpServletRequest request){
    String token = request.getHeader("Authorization");

    if(token == null || token.isEmpty() || token.startsWith("Bearer ")){
      return null;
    }
    else
    {
      return token;
    }
  }


}
