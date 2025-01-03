package com.example.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.thymeleaf.domain.entity.User;
import com.example.thymeleaf.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
  private UserRepository repository;

  @Override
  public User findById(Long uno) {
    Optional<User> todoOpt = repository.findById(uno);
    if(!todoOpt.isPresent()) return null;
    return todoOpt.get();
  }

  @Override
  public List<User> list() {
    return repository.findAll();
  }
  
  @Override
  public Long write(User user) {
    return repository.save(user).getUno();
  }
  
  @Override
  public void modify(User user) {
    repository.save(user);
  }

  @Override
  public void remove(Long uno) {
    repository.deleteById(uno);
  }

  

}
