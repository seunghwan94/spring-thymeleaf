package com.example.thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thymeleaf.domain.entity.Reply;
import com.example.thymeleaf.domain.entity.Todo;


@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer>{
  Long countByTodo(Todo todo);
  void deleteByTodo_Tno(Long tno);
  void deleteByUser_Uno(Long uno);

}
