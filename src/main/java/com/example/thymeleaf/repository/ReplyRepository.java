package com.example.thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.thymeleaf.domain.entity.Reply;


@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer>{
  @Query("DELETE FROM tbl_reply r WHERE r.tno = :tno")
  void deleteByTodo(@Param("tno") Long tno);

  @Query("DELETE FROM tbl_reply r WHERE r.uno = :uno")
  void deleteByUser(@Param("uno") Long uno);

}
