package com.example.thymeleaf.service;

import java.util.List;

import com.example.thymeleaf.domain.entity.Todo;

public interface TodoService {
  Todo findById(Long tno);
  List<Todo> list();
  Long write(Todo todo);

  void modify(Todo todo);
  void remove(Todo todo);
}
