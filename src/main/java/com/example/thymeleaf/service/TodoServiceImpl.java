package com.example.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.thymeleaf.domain.entity.Todo;
import com.example.thymeleaf.repository.ReplyRepository;
import com.example.thymeleaf.repository.TodoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
  private TodoRepository todoRepository;
  private ReplyRepository replyRepository;

  @Override
  public Todo findById(Long tno) {
    Optional<Todo> todoOpt = todoRepository.findById(tno);
    if(!todoOpt.isPresent()) return null;
    return todoOpt.get();
  }

  @Override
  public List<Todo> list() {
    return todoRepository.findAll();
  }
  
  @Override
  public Long write(Todo todo) {
    return todoRepository.save(todo).getTno();
  }
  
  @Override
  public void modify(Todo todo) {
    todoRepository.save(todo);
  }

  @Override
  public void remove(Todo todo) {

    // replyRepository.deleteByTodo(todo.getTno());
    // todoRepository.deleteById(todo.getTno());
  }

  

}
