package com.example.thymeleaf.domain.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDto {
  private Long tno;
  private String title;
  private String content;
  private String userEmail;
  private LocalDateTime regDate;
  private LocalDateTime modDate;
  private Long replyCnt;
}
