package com.example.thymeleaf.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.thymeleaf.domain.entity.User;
import com.example.thymeleaf.repository.ReplyRepository;
import com.example.thymeleaf.repository.TodoRepository;
import com.example.thymeleaf.repository.UserRepository;

import jakarta.transaction.Transactional;


@SpringBootTest
public class UserRepositoryTest {
  @Autowired
  private UserRepository repository;
  @Autowired
  private TodoRepository todoRepository;
  @Autowired
  private ReplyRepository replyRepository;

  @Test
  public void testSave(){
    User saveUser = repository.save(User.builder()
      .email("test")
      .password("1234")
      .name("연습")
    .build());

    Optional<User> user = repository.findById(saveUser.getUno());

    assert(user).isPresent();
    assertNotNull(user.get().getUno());
  }


  @Test
  @Transactional
  @Rollback(false)
  public void testUpdate(){
    User saveUser = repository.save(User.builder()
      .uno(11L)
      .email("test1")
      .password("1234")
      .name("연습")
    .build());

    Optional<User> user = repository.findById(saveUser.getUno());
    
    assert(user).isPresent();
    assertNotNull(user.get().getUno());
  }

  @Test
  public void testSelectAll(){
    List<User> users = repository.findAll();
    assertNotNull(users);
    assertTrue(users.size() > 0);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testDelete(){
    // target
    Long uno = 14L;
    Optional<User> user = repository.findById(uno);
    assertTrue(user.isPresent());

    // when
    replyRepository.deleteByUserUno(user.get().getUno());
    todoRepository.deleteByUserUno(user.get().getUno());
    repository.deleteById(user.get().getUno());

    // then
    user = repository.findById(uno);
    assertTrue(user.isEmpty());
  }

}
