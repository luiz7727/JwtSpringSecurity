package com.example.studyjwt.Security;

import com.example.studyjwt.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
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

@EnableWebSecurity
@Configuration
public class Security {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserDAO userDAO;

  @Bean
  public PasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    //SessionCreationPolicy.STATELESS estamos fazendo conexão de forma stateless e não em forma de sessão.

    httpSecurity
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.POST,"/auth").permitAll()
            .anyRequest().authenticated().and()
            .csrf()
            .disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(new AuthenticationViaTokenFilter(tokenService,userDAO), UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
    return new CustomAuthenticationManager();
  }

}
