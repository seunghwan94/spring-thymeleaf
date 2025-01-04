package com.example.thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.thymeleaf.domain.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
  
  @Query("delete from tbl_todo t where t.user.uno = :uno")
  int deleteByUser(@Param("uno") Long uno);

}
