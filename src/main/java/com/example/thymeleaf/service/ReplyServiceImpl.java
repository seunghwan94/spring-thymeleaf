package com.example.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.thymeleaf.domain.entity.Reply;
import com.example.thymeleaf.repository.ReplyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService{
  private ReplyRepository repository;

  @Override
  public Reply findById(int rno) {
    Optional<Reply> relpyOpt = repository.findById(rno);
    if(!relpyOpt.isPresent()) return null;
    return relpyOpt.get();
  }

  @Override
  public List<Reply> list() {
    return repository.findAll();
  }
  
  @Override
  public int write(Reply relpy) {
    return repository.save(relpy).getRno();
  }
  
  @Override
  public void modify(Reply relpy) {
    repository.save(relpy);
  }

  @Override
  public void remove(int rno) {
    repository.deleteById(rno);
  }

  

}
