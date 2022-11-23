package com.example.studyjwt.DAO;

import com.example.studyjwt.MODEL.User;
import com.example.studyjwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDAO {

  @Autowired
  private UserRepository userRepository;

  public Optional<User> getUserByName(String username){
    return userRepository.findByName(username);
  }
}
