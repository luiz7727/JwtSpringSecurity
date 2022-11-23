package com.example.studyjwt.Security;

import com.example.studyjwt.DAO.UserDAO;
import com.example.studyjwt.MODEL.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserDAO userDAO;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDAO.getUserByName(username).orElseThrow(() -> new UsernameNotFoundException("Esse cliente não existe"));

    return new UserDetailsImpl(user);
  }
}
