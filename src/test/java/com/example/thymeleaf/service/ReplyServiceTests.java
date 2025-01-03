package com.example.thymeleaf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.thymeleaf.domain.entity.Reply;
import com.example.thymeleaf.domain.entity.Todo;
import com.example.thymeleaf.repository.TodoRepository;


@SpringBootTest
public class ReplyServiceTests {
  @Autowired
  private TodoRepository todoRepository;
  @Autowired
  private ReplyService service;

  @Test
  public void testList(){
    // when
    List<Reply> relpy = service.list();
    // then
    assertTrue(relpy.size() > 0);
  }

  @Test
  public void testWrite(){
    // target
    Long tno = 7L;
    Optional<Todo> todoOpt = todoRepository.findById(tno);
    assertNotNull(todoOpt.isPresent());
    
    // get
    Reply relpy = Reply.builder()
      .text("email@naver.com")
      .todo(todoOpt.get())
      .user(todoOpt.get().getUser())
    .build();
    // when
    int rno = service.write(relpy);
    // then
    assertTrue(rno > 0);
  }

  @Test
  public void testModify(){
    // target
    int rno = 2;
    // get
    Reply relpy = service.findById(rno);
    Reply modifyReply = Reply.builder()
      .rno(relpy.getRno())
      .text("email@naver.com")
      .todo(relpy.getTodo())
      .user(relpy.getUser())
    .build();
    // when
    service.modify(modifyReply);
    // then
    relpy = service.findById(rno);
    assertEquals("email@naver.com",relpy.getText());
  }

  @Test
  public void testRemove(){
    // target
    int rno = 4;
    // get
    Reply relpy = service.findById(rno);
    assertNotNull(relpy);
    // when
    service.remove(relpy.getRno());
    // then
    relpy = service.findById(rno);
    assertNull(relpy);
  }
}
