package com.example.studyjwt.Security;

import com.example.studyjwt.DAO.UserDAO;
import com.example.studyjwt.MODEL.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

public class CustomAuthenticationManager implements AuthenticationManager {

  @Autowired
  private UserDAO userDAO;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {

    try
    {
      User user = userDAO.getUserByName(auth.getName()).get();

      //verificando password
      System.out.println("SENHA DO DTO: " + auth.getCredentials().toString());
      System.out.println("SENHA DO CLIENTE NO BANCO: " +user.getPassword());
      if(user.getPassword().equalsIgnoreCase(auth.getCredentials().toString())){
        return auth;
      }

    }
    catch (NullPointerException e)
    {
      throw new BadCredentialsException("Usuário não cadastrado");
    }

    throw new BadCredentialsException("Senha incorreta");
  }


}
