package com.example.thymeleaf.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.thymeleaf.domain.entity.Todo;
import com.example.thymeleaf.domain.entity.User;
import com.example.thymeleaf.repository.ReplyRepository;
import com.example.thymeleaf.repository.TodoRepository;
import com.example.thymeleaf.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;


@SpringBootTest
@Log4j2
public class TodoRepositoryTest {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ReplyRepository replyRepository;
  @Autowired
  private TodoRepository repository;

  @Test
  public void testSave(){
    Optional<User> user = userRepository.findById(4L);
    assert(user).isPresent();

    Todo saveTodo = repository.save(Todo.builder()
      .title("test")
      .content("연습")
      .user(user.get())
    .build());

    assertNotNull(saveTodo.getUser());
    assertNotNull(saveTodo.getUser().getUno());
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testUpdate(){
    Optional<User> user = userRepository.findById(2L);
    assert(user).isPresent();

    Todo saveTodo = repository.save(Todo.builder()
      .tno(user.get().getUno())
      .title("test")
      .content("연습")
      .user(user.get())
    .build());

    Optional<Todo> todo = repository.findById(saveTodo.getTno());
    
    assert(todo).isPresent();
    assertNotNull(todo.get().getTno());
  }

  @Test
  public void testSelectAll(){
    List<Todo> todos = repository.findAll();
    assertNotNull(todos);
    assertTrue(todos.size() > 0);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testDelete(){
    // target
    Long tno = 13L;
    
    Optional<Todo> todo = repository.findById(tno);
    assertTrue(todo.isPresent());
    // when
    replyRepository.deleteByTodoTno(todo.get().getTno());
    repository.deleteById(todo.get().getTno());
    // then
    todo = repository.findById(tno);
    assertTrue(todo.isEmpty());
  }

  @Test
  public void testGetTodoByTno(){
    Long tno = 9L;
    Object[] result = (Object[])repository.getTodoByTno(tno);
    assertNotNull(result);
  }

}
