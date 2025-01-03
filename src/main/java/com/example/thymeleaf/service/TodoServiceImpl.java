package com.example.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.thymeleaf.domain.entity.Todo;
import com.example.thymeleaf.repository.TodoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
  private TodoRepository repository;

  @Override
  public Todo findById(Long tno) {
    Optional<Todo> todoOpt = repository.findById(tno);
    if(!todoOpt.isPresent()) return null;
    return todoOpt.get();
  }

  @Override
  public List<Todo> list() {
    return repository.findAll();
  }
  
  @Override
  public Long write(Todo todo) {
    return repository.save(todo).getTno();
  }
  
  @Override
  public void modify(Todo todo) {
    repository.save(todo);
  }

  @Override
  public void remove(Long tno) {
    repository.deleteById(tno);
  }

  

}
