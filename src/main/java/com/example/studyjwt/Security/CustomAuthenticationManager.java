package com.example.studyjwt.Security;

import com.example.studyjwt.DAO.UserDAO;
import com.example.studyjwt.MODEL.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class CustomAuthenticationManager implements AuthenticationManager {

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {

    try
    {
      User user = userDAO.getUserByName(auth.getName()).get();

      //verificando password
      if(encoder.matches(auth.getCredentials().toString(),user.getPassword())){
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
