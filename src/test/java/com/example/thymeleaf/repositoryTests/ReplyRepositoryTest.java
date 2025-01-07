package com.example.thymeleaf.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.thymeleaf.domain.entity.Reply;
import com.example.thymeleaf.domain.entity.Todo;
import com.example.thymeleaf.domain.entity.User;
import com.example.thymeleaf.repository.ReplyRepository;
import com.example.thymeleaf.repository.TodoRepository;
import com.example.thymeleaf.repository.UserRepository;

import jakarta.transaction.Transactional;


@SpringBootTest
public class ReplyRepositoryTest {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TodoRepository todoRepository;
  @Autowired
  private ReplyRepository repository;

  @Test
  public void testSave(){
    Optional<User> user = userRepository.findById(2L);
    assert(user).isPresent();

    Optional<Todo> todo = todoRepository.findById(2L);
    assert(todo).isPresent();

    Reply saveReply = repository.save(Reply.builder()
      .text("test")
      .user(user.get())
      .todo(todo.get())
    .build());

    assertNotNull(saveReply.getUser());
    assertNotNull(saveReply.getUser().getUno());
  }


  @Test
  @Transactional
  @Rollback(false)
  public void testUpdate(){
    Optional<User> user = userRepository.findById(2L);
    assert(user).isPresent();

    Optional<Todo> todo = todoRepository.findById(2L);
    assert(todo).isPresent();

    Optional<Reply> reply = repository.findById(1);
    assert(reply).isPresent();

    Reply saveReply = repository.save(Reply.builder()
      .rno(reply.get().getRno())
      .text("test2")
      .user(user.get())
      .todo(todo.get())
    .build());


    reply = repository.findById(saveReply.getRno());
    
    assert(reply).isPresent();
    assertNotNull(reply.get().getRno());
  }

  @Test
  public void testSelectAll(){
    List<Reply> replies = repository.findAll();
    assertNotNull(replies);
    assertTrue(replies.size() > 0);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testDelete(){
    // target
    int rno = 16;
    Optional<Reply> reply = repository.findById(rno);
    assertTrue(reply.isPresent());
    
    repository.deleteById(reply.get().getRno());
    reply = repository.findById(rno);
    assertTrue(reply.isEmpty());
  }

}
