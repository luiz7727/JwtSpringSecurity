package com.example.studyjwt.DAO;

import com.example.studyjwt.MODEL.User;
import com.example.studyjwt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {

  @Autowired
  private UserRepository userRepository;

  public User getUserByName(String username){
    return userRepository.findByName(username).get();
  }
}
