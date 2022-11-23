package com.example.studyjwt.Controller;

import com.example.studyjwt.DTO.LoginFormDTO;
import com.example.studyjwt.DTO.TokenDTO;
import com.example.studyjwt.Security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController{


  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private PasswordEncoder encoder;

  @PostMapping("/auth")
  public ResponseEntity<TokenDTO> authentication(@RequestBody LoginFormDTO loginFormDTO){


    loginFormDTO.setPassword(encoder.encode(loginFormDTO.getPassword()));
    System.out.println(loginFormDTO.getPassword());
    UsernamePasswordAuthenticationToken dadosLogin = loginFormDTO.converter();

    try
    {
      Authentication authentication = authManager.authenticate(dadosLogin);
      String token = tokenService.gerarToken(authentication);
      System.out.println(token);
      return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
    }
    catch (AuthenticationException e)
    {
      System.out.println(e);
      return ResponseEntity.badRequest().build();
    }

  }


}
