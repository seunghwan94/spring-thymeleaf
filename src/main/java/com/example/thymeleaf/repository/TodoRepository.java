package com.example.thymeleaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.thymeleaf.domain.entity.Todo;

/*
 *  Jpql
 *  @Query("delete from tbl_todo t where t.user.uno = :uno")
 *    기본적으로 select만 지원하기 때문에 delete 나 update 시 @Modifying 어노테이션 추가해야된다.
 *    또한 @Transactional 적용해야 함
 * 
 */

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
  
  List<Todo> findByUserUno(Long uno);
  void deleteByUserUno(Long uno);
  

  @Query("select count(r), u, t\r\n" + //
         "FROM tbl_todo t\r\n" + //
         "left join user u\r\n" + //
         "left join tbl_reply r on t = r.todo\r\n" + //
         "where t.tno = :tno")
  Object getTodoByTno(@Param("tno") Long tno);

}
