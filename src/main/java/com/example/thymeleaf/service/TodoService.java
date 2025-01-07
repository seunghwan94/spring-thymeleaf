package com.example.thymeleaf.service;

import java.util.List;

import com.example.thymeleaf.domain.dto.TodoDto;
import com.example.thymeleaf.domain.entity.Todo;
import com.example.thymeleaf.domain.entity.User;

public interface TodoService {

  Todo findById(Long tno);
  List<Todo> list();
  Long write(Todo todo);

  void modify(Todo todo, Long tno);
  void remove(Long tno);

  default Todo toEntity(TodoDto dto){
    return Todo.builder()
      .tno(dto.getTno())
      .title(dto.getTitle())
      .content(dto.getContent())
      .user(User.builder()
        .email(dto.getUserEmail())
        .build())
    .build();
  }

  default TodoDto toDto(Todo todo, Long replyCnt){
    return TodoDto.builder()
      .tno(todo.getTno())
      .title(todo.getTitle())
      .content(todo.getContent())
      .userEmail(todo.getUser().getEmail())
      .regDate(todo.getRegDate())
      .modDate(todo.getModDate())
      .replyCnt(replyCnt)
    .build();
  }
}
